package com.ywc.notification.pincodes.services;

import static java.util.Objects.isNull;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.ywc.notification.pincodes.configuration.PinCodeConfiguration;
import com.ywc.notification.pincodes.constants.CustomSearchColumns;
import com.ywc.notification.pincodes.dto.PincodeResult;
import com.ywc.notification.pincodes.dto.SearchColumn;
import com.ywc.notification.pincodes.util.CsvSearch;

@Service
public class PinCodeServiceImpl<T> implements PincodesService {
//	@Autowired
//	private PinCodeConfiguration pinCodeConfiguration;
	
	@Value("${application.config.shadowfax.csv-path}")
	private String filePath; 

	@Override
	public Set<String> getAllPincodeByCity(String city) {
		Set<String> pinCodes = new HashSet<String>();
		SearchColumn searchColumn = new SearchColumn();
		searchColumn.setColumnKey("city");
		searchColumn.setColumnValue(city);
		pinCodes = (Set<String>) getListCityByState(null, city);
		return pinCodes;
	}

	public Set<T> getListCityByState(String state, String city) {
		Set<String> cities = new HashSet<String>();

		SearchColumn searchColumn = new SearchColumn();
		if (!isNull(city)) {
			searchColumn.setColumnKey("city");
			searchColumn.setColumnValue(city);
		}
		List<PincodeResult> codeSearchResults = searchCustom(searchColumn);
		if (!isNull(state))
			cities.addAll(codeSearchResults.stream().map(mpr -> mpr.getCity()).collect(Collectors.toSet()));
		else {

			Set<String> postCodelist = codeSearchResults.stream().map(mpr -> mpr.getPincode())
					.collect(Collectors.toSet());
			cities = postCodelist.stream().map(Object::toString).collect(Collectors.toSet());
		}
		return (Set<T>) cities;
	}

	public List<PincodeResult> searchCustom(final SearchColumn searchColumn) {
		long start = System.currentTimeMillis();
		CsvParserSettings settings = new CsvParserSettings();
		settings.setHeaderExtractionEnabled(true); // extract headers from the first row

		CustomSearchColumns customSearchColumns = getKeyColumn(searchColumn);
		CsvSearch search = new CsvSearch(customSearchColumns.getColumnName(), searchColumn.getColumnValue());

		// instruct the parser to send all rows parsed to your custom RowProcessor.
		settings.setProcessor(search);
		// Finally, create a parser
		CsvParser parser = new CsvParser(settings);

		parser.parse(new File(filePath));
		List<String[]> results = search.getRows();

		List<PincodeResult> result = results.parallelStream().map(mpr -> getResultSets(mpr))
				.collect(Collectors.toList());
		System.out.println("Rows matched: " + results.size());
		System.out.println("Time taken: " + (System.currentTimeMillis() - start) + " ms");
		return result;
	}

	private PincodeResult getResultSets(String[] mpr) {
		if (!isNull(mpr)) {
			PincodeResult result = new PincodeResult(mpr[0], mpr[1], mpr[2], mpr[3]);
			return result;
		}
		return null;
	}

	private CustomSearchColumns getKeyColumn(final SearchColumn searchColumn) {
		switch (searchColumn.getColumnKey()) {
		case "zone":
			return CustomSearchColumns.zone;
		case "city":
			return CustomSearchColumns.city;
		case "state":
			return CustomSearchColumns.state;
		default:
			break;
		}
		return null;

	}

}
