package com.ywc.notification.hsn.service;

import static java.util.Objects.isNull;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.ywc.notification.hsn.client.HsnCodeClient;
import com.ywc.notification.hsn.model.HsnCodeModel;

import lombok.extern.slf4j.Slf4j;

@PropertySource(value = "classpath:application.yml")
@Component
@CacheConfig(cacheNames = {"HSNCODEMODEL"})
@Slf4j
public class HsnCodeServiceImpl implements HsnCodeService{

	@Autowired
	HsnCodeClient hsnCodeclnt;

	@Value("${gst.validationApi.key}")
	private String apiKey;

	private final MongoTemplate template;

	private final String mongoCollection = "hsnCodeModel";

	@Autowired
	public HsnCodeServiceImpl(MongoTemplate template) {
		this.template = template;
	}

	@Autowired
	CacheManager cacheManager;


	@Cacheable(unless = "#result == null")
	public HsnCodeModel getHsnCodeModelByCode(String hsnCode) {
		log.info("Executing getHsnCodeModelByCode for model:{} \tCache MISS!", hsnCode);
		Query query = new Query(Criteria.where("hsn_code").is(hsnCode));
		HsnCodeModel codeModel = template.findOne(query, HsnCodeModel.class, mongoCollection);
		log.info(getCoffeeCacheStats().toString());
		return codeModel;
	}


	public HsnCodeModel getHsnCodeGstFrCache() {
		CaffeineCache caffeineCache = (CaffeineCache) cacheManager.getCache("HSNCODEMODEL");
		Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
		for (Map.Entry<Object, Object> entry : nativeCache.asMap().entrySet()) {
			System.out.println("Key = " + entry.getKey());
			System.out.println("Value = " + entry.getValue());
		}
		return null;
	}

	public CacheStats getCoffeeCacheStats() {
		org.springframework.cache.Cache cache = cacheManager.getCache("HSNCODEMODEL");
		Cache nativeCoffeeCache = (Cache) cache.getNativeCache();
		// System.out.println(nativeCoffeeCache.asMap().toString()+"**************");
		return nativeCoffeeCache.stats();
	}

	private HsnCodeModel createHsnCode(HsnCodeModel hsnCodeModel) {
		log.info("Executing createHsnCode, model:{}", hsnCodeModel.getHsn_code());
		try {
			template.insert(hsnCodeModel,mongoCollection);
		} catch (DuplicateKeyException dke) {
			dke.printStackTrace();
		}
		return template.findOne(
				new Query(Criteria.where("hsn_code").is(hsnCodeModel.getHsn_code())),
				HsnCodeModel.class, mongoCollection);//has id
	}

	private boolean checkHsnCodeExist(String hsnCode) {
		HsnCodeModel model = template.findOne(
				new Query(Criteria.where("hsn_code").is(hsnCode)),
				HsnCodeModel.class, mongoCollection);
		if(isNull(model)) 
			return true;
		else 
			return false;

	}


	@CacheEvict(key="#hsnCodeModel.hsn_code")
	public void updateHsnCode(HsnCodeModel hsnCode) {
		log.info("Executing updateHsnCode, hsnCode:{} gstRate: {} Cache Evict!",
				hsnCode.getHsn_code(), hsnCode.getRate_revision());
		try {
			template.save(hsnCode, mongoCollection);
		} catch (DuplicateKeyException dke) {
			dke.printStackTrace();
		}
	}

	@CacheEvict(key = "#hsnCodeModel.hsn_code")
	public void removeHsnCode(HsnCodeModel hsnCodeModel) {
		log.info("Executing removeHsnCode, model:{} \tCache Evict!", hsnCodeModel.getHsn_code());
		template.remove(hsnCodeModel, mongoCollection);
	}

	@Caching(evict = {
			@CacheEvict(value = "HSNCODEGST", allEntries = true),
			@CacheEvict(value = "SECOND_CACHE", allEntries = true)
	})

	public void clearAllCaches() {
		log.info("Cleared all caches");
	}

	@CachePut(key = "#hsnCodeModel.hsn_code", condition = "#hsnCodeModel.hsn_code != 0", unless = "#result == null")
	public HsnCodeModel getHSNCodeGSTRateInfo(String hsnCode) throws Exception {
		if(!checkHsnCodeExist(hsnCode)) {
			return getHsnCodeModelByCode(hsnCode);
		}
		String gstDetails = hsnCodeclnt.getHSNCodeGSTRateInfo(apiKey, hsnCode);
		if (gstDetails != null && !"".equals(gstDetails)) {
			HsnCodeModel hsnCodeModel = createHsnData(gstDetails);
			return createHsnCode(hsnCodeModel);
			//getHsnCodeGstFrCache();
			//str =  getGstRateForHSN(hsnCodeModel);
		}
		log.info(getCoffeeCacheStats().toString());
		 return null;
	}

	private String getGstRateForHSN(HsnCodeModel  hsnCodeModel){
		if(!isNull(hsnCodeModel)){
			String rate = hsnCodeModel.getRate();
			String gstRate = rate.substring(rate.lastIndexOf('/') + 1);
			return gstRate;
		}
		return "0.0";
	}

	private HsnCodeModel createHsnData(String jsonString) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode coderollsJSONObject = objectMapper.readTree(jsonString);
			String cess = coderollsJSONObject.get("cess").asText() !=  null &&  !"".equals(coderollsJSONObject.get("cess").asText() ) ?  coderollsJSONObject.get("cess").asText() : ""  ;   
			String chapter_name = coderollsJSONObject.get("chapter_name").asText()  !=  null && !"".equals(coderollsJSONObject.get("chapter_name").asText()  ) ? coderollsJSONObject.get("chapter_name").asText() : ""   ;
			String chapter_number = coderollsJSONObject.get("chapter_number").asText()  !=  null &&  !"".equals(coderollsJSONObject.get("chapter_number").asText()  ) ?   coderollsJSONObject.get("chapter_number").asText() :""  ;
			String description = coderollsJSONObject.get("description").asText()  !=  null &&  !"".equals(coderollsJSONObject.get("description").asText()  ) ?  coderollsJSONObject.get("description").asText() : ""    ;
			String effective_date =  coderollsJSONObject.get("effective_date").asText()  !=  null &&  !"".equals(coderollsJSONObject.get("effective_date").asText()  ) ?  coderollsJSONObject.get("effective_date").asText() : ""   ;
			String error = coderollsJSONObject.get("error").asText()  !=  null &&  !"".equals(coderollsJSONObject.get("error").asText()  ) ?  coderollsJSONObject.get("error").asText() : ""   ;
			String hsn_code =  coderollsJSONObject.get("hsn_code").asText()  !=  null &&  !"".equals(coderollsJSONObject.get("hsn_code").asText()  ) ?  coderollsJSONObject.get("hsn_code").asText()  : ""   ;
			String rate = coderollsJSONObject.get("rate").asText()  !=  null &&  !"".equals(coderollsJSONObject.get("rate").asText()  ) ?  coderollsJSONObject.get("rate").asText()  : ""   ;
			String rate_revision = coderollsJSONObject.get("rate_revision").asText()  !=  null &&  !"".equals(coderollsJSONObject.get("rate_revision") ) ?  coderollsJSONObject.get("rate_revision").asText()  : ""   ;
			String type = coderollsJSONObject.get("type").asText()  !=  null &&  !"".equals(coderollsJSONObject.get("type").asText()  ) ?  coderollsJSONObject.get("type").asText()  : ""   ;
			//return new HsnCodeModel(cess, chapter_name, chapter_number, description, effective_date, error, hsn_code, rate, rate_revision, type);
			return HsnCodeModel.builder()
					.cess(cess)
					.chapter_name(chapter_name)
					.chapter_number(chapter_number)
					.description(description)
					.effective_date(effective_date)
					.error(error)
					.hsn_code(hsn_code)
					.rate(rate)
					.rate_revision(rate_revision)
					.type(type).build();

		} catch (Exception e) { 
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
