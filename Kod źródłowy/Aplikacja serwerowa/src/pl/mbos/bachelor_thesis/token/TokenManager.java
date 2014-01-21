package pl.mbos.bachelor_thesis.token;

import java.util.HashMap;
import java.util.Map;

import pl.mbos.bachelor_thesis.RandomAlphaNum;

public class TokenManager {
	private static final int TOKEN_LENGTH = 40;

	Map<Long,String> map = new HashMap<Long, String>();
	
	private static TokenManager instance;
	
	public static TokenManager getInstance() {
		if(instance == null) {
			instance = new TokenManager();
		}
		return instance;
	}
	
	private TokenManager() {
		
	}

	public String generateToken(long userID) {
		String token = RandomAlphaNum.gen(TOKEN_LENGTH);
		map.put(userID,token);
		return token;
	}
	
	public boolean verifyToken(String token) {
		boolean outcome = map.containsValue(token);
		if(outcome) {
			map.values().remove(token);
		}
		return outcome;
	}
		
	
}
