package pl.mbos.bachelor_thesis.web.helpers;

public class OperationResult {
	public String outcome;
	public Integer code;

	public OperationResult() {

	}

	public OperationResult(Integer code, String outcome) {
		this.outcome = outcome;
		this.code = code;
	}

}
