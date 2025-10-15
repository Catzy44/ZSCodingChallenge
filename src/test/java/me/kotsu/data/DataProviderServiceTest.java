package me.kotsu.data;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import me.kotsu.data.dumb.DumbDataProvider;
import me.kotsu.data.dumb.DumbDataProviderConfig;
import me.kotsu.exceptions.ParsingException;
import me.kotsu.parser.Parser;
import me.kotsu.parser.ParsersRegistry;

public class DataProviderServiceTest {
	private static final Parser testParser = ParsersRegistry.JSON.get();
	
    @Test
	public void returnsNothingIfNoDataSourcesPresent() {
		DataProviderService dataProviderService = DataProviderService.builder()
				.build();
		
		Optional<int[]> serviceResponse = dataProviderService
				.streamValidParsedData(testParser)
				.findFirst();
		assertTrue(serviceResponse.isEmpty(), "stream without any valid sources started should be empty!");
	}
	
	@Test
	public void returnsProperlyParsedStreamIfOneValidSourcePresent() throws IOException, ParsingException {
		String validData = "{\"elements\":[10,13,-5,50,0]}";
		
		DataProviderService dataProviderService = DataProviderService.builder()
				.add(new DumbDataProvider(new DumbDataProviderConfig(Optional.of(validData))))
				.build();
		
		Optional<int[]> decodedValidData = testParser.parse(validData);
		assertTrue(decodedValidData.isPresent(), "value from parser should be present");
		
		Optional<int[]> serviceResponse = dataProviderService
				.streamValidParsedData(testParser)
				.findFirst();
		assertTrue(serviceResponse.isPresent(), "value from service should be present");
		
		assertArrayEquals(decodedValidData.get(), serviceResponse.get(), "directly decoded data should match data from the DataProviderService");
	}
	
	@Test
	public void skipsInvalidDataSourcesAndReturnsFirstValid() throws IOException, ParsingException {
		String invalidData = "{\"elem_INVALIDFORMAT_ents\":[10,13,-5,50,0]}";
		String invalidData1 = "{\"elements\":[10,13,-5,50,0]INVALIDSYNTAX";
		String validData = "{\"elements\":[10,13,-5,50,0]}";// <- shoudl return this one!
		String validData1 = "{\"elements\":[11,14,-5,50,0]}";
		String invalidData2 = "{\"elemINVALIDFORMATents\":[10,13,-5,50,0]}";
		
		Optional<int[]> decodedValidDataItShouldReturn = testParser.parse(validData);
		assertTrue(decodedValidDataItShouldReturn.isPresent(), "the FIRST valid value should be successfully parsed");
		
		Optional<int[]> decodedValidDataItShouldNOTReturn = testParser.parse(validData);
		assertTrue(decodedValidDataItShouldNOTReturn.isPresent(), "the SECOND valid value should be successfully parsed");
		
		
		DataProviderService dataProviderService = DataProviderService.builder()
				.add(new DumbDataProvider(new DumbDataProviderConfig(Optional.of(invalidData))))
				.add(new DumbDataProvider(new DumbDataProviderConfig(Optional.of(invalidData1))))
				.add(new DumbDataProvider(new DumbDataProviderConfig(Optional.of(validData))))
				.add(new DumbDataProvider(new DumbDataProviderConfig(Optional.of(validData1))))
				.add(new DumbDataProvider(new DumbDataProviderConfig(Optional.of(invalidData2))))
				.build();
		
		Optional<int[]> serviceResponse = dataProviderService
				.streamValidParsedData(testParser)
				.findFirst();
		assertTrue(serviceResponse.isPresent(), "value from service should be present (second source is valid)");
		
		assertArrayEquals(decodedValidDataItShouldReturn.get(), serviceResponse.get(), "directly decoded data should match data from the DataProviderService (second source)");
	}
	
	@Test
	public void returnsNothingIfNoValidData() throws IOException {
		String invalidData = "{\"elem_INVALIDFORMAT_ents\":[10,13,-5,50,0]}";
		String invalidData1 = "{\"elements\":[10,13,-5,50,0]INVALIDSYNTAX";
		
		DataProviderService dataProviderService = DataProviderService.builder()
				.add(new DumbDataProvider(new DumbDataProviderConfig(Optional.of(invalidData))))
				.add(new DumbDataProvider(new DumbDataProviderConfig(Optional.of(invalidData1))))
				.build();
		
		Optional<int[]> serviceResponse = dataProviderService
				.streamValidParsedData(testParser)
				.findFirst();
		assertTrue(serviceResponse.isEmpty(), "response should be empty - no valid data");
	}
}
