package pl.mbos.bachelor_thesis.web.helpers;

public class ParseResult {
	public String outcome;
	public Boolean success;

	public ParseResult() {

	}

	public ParseResult(Boolean success, String reason) {
		this.outcome = outcome;
		this.success = success;
	}
}
