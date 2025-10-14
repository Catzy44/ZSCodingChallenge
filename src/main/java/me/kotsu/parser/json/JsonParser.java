package me.kotsu.parser.json;

import java.util.Optional;

import com.google.gson.Gson;

import me.kotsu.exceptions.ParsingException;
import me.kotsu.parser.Parser;
import me.kotsu.parser.json.dto.DataWrapperDTO;

public class JsonParser implements Parser {
	
	private static final Gson gson = new Gson();

	@Override
	public Optional<int[]> parse(String data) throws ParsingException {
		try {
			DataWrapperDTO dataWrapperDTO = gson.fromJson(data, DataWrapperDTO.class);
			if (dataWrapperDTO == null || dataWrapperDTO.elements() == null) {
                return Optional.empty();
            }
			return Optional.of(dataWrapperDTO.elements());
		} catch(Exception e) {
			throw new ParsingException("Parsing failed:", e);
		}
	}

}
