/**
 * File: User.java
 *
 * Description: User class for Login system
 *
 * @author Pranav Bheda
 * @author Gurneev Sareen
 */

package finalProject;

public class User{

    private String password, username;

    public User(final String username, final String password){
        setUsername(username);
        setPassword(password);
    }

    /**
     * @return the password
     */
    public String getPassword(){
        return password;
    }

    /**
     * @return the username
     */
    public String getUsername(){
        return username;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(final String password){
        this.password = password;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(final String username){
        this.username = username;
    }
}