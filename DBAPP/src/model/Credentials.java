package model;

public class Credentials {

	
	private String loginName;
	private String loginPassword;
	
	
	public Credentials(String loginName, String loginPassword)
	{
		this.loginName = loginName;
		this.loginPassword = loginPassword;
	}


	public String getLoginName() {
		return loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	@Override
	public String toString() {
		return "Credentials [loginName=" + loginName + ", loginPassword=" + loginPassword + "]";
	}

	
	
}

	

