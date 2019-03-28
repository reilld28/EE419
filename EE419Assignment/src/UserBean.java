

public class UserBean {
	
    private String email;
    private String password;
    private String name;
    public boolean valid;
	
	
    public String getmame() {
       return name;
	}

    public void setname(String newname) {
       name = newname;
	}


    public String getPassword() {
       return password;
	}

    public void setPassword(String newPassword) {
       password = newPassword;
	}
	
			
    public String getemail() {
       return email;
			}

    public void setemail(String newemail) {
       email = newemail;
			}

				
    public boolean isValid() {
       return valid;
	}

    public void setValid(boolean newValid) {
       valid = newValid;
	}	
}
