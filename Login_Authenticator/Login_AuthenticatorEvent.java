/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login_authenticator;

import java.awt.event.*;
import java.sql.*;
import java.security.*;
import java.util.Arrays;


/**
 *
 * @author Jason
 */
public class Login_AuthenticatorEvent implements ActionListener, ItemListener, KeyListener, FocusListener
{
    Login_Authenticator gui ;
    
    public Login_AuthenticatorEvent(Login_Authenticator input)
    {
        gui = input;
    }
    
    public void actionPerformed(ActionEvent event)
    {
        String theEvent = event.getActionCommand();
        switch (theEvent)
        {
            case "Login":
                login();
                break;
            case "Unlock":
                unlock();
                break;
            case "Create Account":
                gui.displayCreate();
                break;
            case "Create":
                createAccount();
                break;
            case "Back to Login":
                gui.displayLogin();
                break;
            case "Back to Admin":
                gui.displayAdmin();
                break;
            case "Unlock Account":
                gui.displayUnlock();
                break;
            case "Logout":
                logout();
                break;
            case "Password Reset":
                gui.displayReset();
                break;
            case "Try Again":
                //System.out.println("I'm seeing the action\n");
                gui.serverArea.setText("Connecting to Database ....");
                tryAgain();
                break;
            case "Exit":
                exit();
                break;
        }
    }
    
    public void itemStateChanged(ItemEvent event)
    {
    }
    
    public void focusLost(FocusEvent event)
    {
        Object theEvent = event.getSource();
        if (theEvent.equals(gui.accountNameInput))
        {
            gui.userName = gui.accountNameInput.getText();
        }
        else if (theEvent.equals(gui.passwordInput))
        {
            gui.password = String.valueOf(gui.passwordInput.getPassword());
        }
        else if (theEvent.equals(gui.accountNameInputCr))
        {
            gui.userName = gui.accountNameInputCr.getText();
        }
        else if (theEvent.equals(gui.passwordInputCr))
        {
            gui.password = String.valueOf(gui.passwordInputCr.getPassword());
        }
    }
    
    public void focusGained(FocusEvent event)
    {
        if (event.getComponent() == gui.accountNameInput)
        {
            gui.accountNameInput.selectAll();
        }
        else if (event.getComponent() == gui.passwordInput)
        {
            gui.passwordInput.selectAll();
        }
        else if (event.getComponent() == gui.accountNameInputCr)
        {
            gui.accountNameInputCr.selectAll();
        }
        else if (event.getComponent() == gui.passwordInputCr)
        {
            gui.passwordInputCr.selectAll();
        }
    }
    
    public void keyPressed (KeyEvent event)
    {
        
    }
    
    public void keyReleased (KeyEvent event)
    {
        
    }
    
    public void keyTyped (KeyEvent event)           
    {
        Object theEvent = event.getSource();

        if (event.getComponent() == gui.accountNameInput)
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                gui.userName = gui.accountNameInput.getText();
                gui.passwordInput.requestFocusInWindow();
            }
        }
        else if (theEvent.equals(gui.passwordInput))
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                gui.password = String.valueOf(gui.passwordInput.getPassword());
                login();
            }
        }
        else if (event.getComponent() == gui.accountNameInputCr)
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                gui.userName = gui.accountNameInputCr.getText();
                gui.passwordInputCr.requestFocusInWindow();
            }
        }
        else if (theEvent.equals(gui.passwordInputCr))
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                gui.password = String.valueOf(gui.passwordInputCr.getPassword());
                createAccount();
            }
        }
    }

    public void unlock()
    {   
        gui.reachable = validConn();
            
            if (gui.reachable == false)
            {
                return;
            }
        try
        {
            gui.query = "begin UPDATE Accounts SET UnlockDate = LOCALTIMESTAMP, LockDate = NULL WHERE UserName = '"
                    + gui.userName + "'; end;";

            gui.stmt.executeUpdate(gui.query);
            //gui.unlock.setVisible(false);
            gui.login.setVisible(true);
            gui.query = "Account Successfully Unlocked!";
            gui.infoArea.setText(gui.query);

        }
            
        catch (SQLException a)
        {
            System.out.println("Exception being thrown\n" + a );
        };
    }
    
    public void tryAgain()
    {           
        try
        {
            //creating connection to Oracle database using JDBC
            gui.conn = DriverManager.getConnection(gui.url,gui.props);
            gui.stmt = gui.conn.createStatement();
            
            //If no Exception
            gui.infoArea.setText("");
            gui.displayLogin();

        }
            
        catch (SQLException a)
        {
            //gui.query = "Cannot connect to the database";
            //gui.serverArea.setText(gui.query);
            System.out.println("Exception being thrown\n" + a );
        };
        gui.query = "\nCannot connect to the database";
        gui.serverArea.append(gui.query);
    }
    
    public void login()
    {   
        if(gui.userName != null && gui.password != null)
        {
            try
            {
                gui.reachable = validConn();

                if (gui.reachable == false)
                {
                    return;
                }
                //gui.userName = gui.accountNameInput.getText();
                //gui.password = String.valueOf(gui.passwordInput.getPassword());

                gui.query = "begin Login_SP(?,?,?,?); end;";
                CallableStatement callStmt = gui.conn.prepareCall(gui.query);

                gui.password = encryptPassword(gui.password);
                System.out.println("Password: "+ gui.password );

                callStmt.setString(1, gui.userName);
                callStmt.setString(2, gui.password);
                callStmt.registerOutParameter(3, Types.INTEGER);
                callStmt.registerOutParameter(4, Types.VARCHAR);
                callStmt.execute();
                gui.loginResult = callStmt.getString(4);
                gui.userNameActive = gui.userName;
                gui.accountNameInput.setText(null);
                gui.passwordInput.setText(null);
                gui.userName = null;
                gui.password = null;
                gui.infoArea.setText(gui.loginResult);
                gui.results = callStmt.getInt(3);
                if (gui.results == 1)
                        {
                            gui.userLabel.setText("User Account: " + gui.userNameActive);
                            gui.displayUser();
                            //gui.displayAdmin();
                        }
            }

            catch (SQLException a)
            {
                System.out.println("Exception being thrown\n" + a );
            };
        }
        else
        {
            gui.accountNameInput.setText(null);
            gui.passwordInput.setText(null);
            gui.userName = null;
            gui.password = null;
            gui.infoArea.setText("ERROR: Must enter an Account and Password");
        }
    }   
    
    public void logout()
    {   
        //try
        //{
            gui.reachable = validConn();
/*
            gui.query = "begin Logout_SP(?,'V'); end;";
            CallableStatement callStmt = gui.conn.prepareCall(gui.query);

            callStmt.setString(1, gui.userNameActive);
            callStmt.execute();
*/
            gui.infoArea.setText("Account: " + gui.userNameActive + "\nLogged Out");
            gui.displayLogin();
                    
        //}
/*
        catch (SQLException a)
        {
            System.out.println("Exception being thrown\n" + a );
        };
        */
    
    } 
    
    public boolean validConn ()
    {
        try
        {
            gui.reachable = gui.conn.isValid(5);
            if (gui.reachable == false)
                {
                    gui.displayServer();
                    gui.repaint();
                }
        }
        catch( SQLException a)
        {
            System.out.println("Exception being thrown\n" + a );
        }
        return gui.reachable;
    }
    
    public void createAccount()
    {
        if(gui.userName != null && gui.password != null)
        {
            try
            {
                gui.reachable = validConn();

                if (gui.reachable == false)
                {
                    return;
                }

                gui.results = 1;

                //gui.userName = gui.accountNameInputCr.getText();
                //gui.password = String.valueOf(gui.passwordInputCr.getPassword());

                System.out.println("Password: "+ gui.password );
                System.out.println("Username: "+ gui.userName );

                if (gui.userName.charAt(0) != Character.toUpperCase(gui.userName.charAt(0)) ||
                    gui.password.charAt(0) != Character.toUpperCase(gui.password.charAt(0)))
                    gui.results = 3;
                if (gui.userName.equalsIgnoreCase(gui.password))
                    gui.results = 3;

                if (gui.results == 1)
                {
                    gui.query = "begin CREATEACCOUNT_SP(?,?,?); end;";
                    CallableStatement callStmt = gui.conn.prepareCall(gui.query);

                    gui.password = encryptPassword(gui.password);
                    System.out.println("Password: "+ gui.password );

                    callStmt.setString(1, gui.userName);
                    callStmt.setString(2, gui.password);
                    callStmt.registerOutParameter(3, Types.INTEGER);
                    callStmt.execute();
                    gui.results = callStmt.getInt(3);
                }

                gui.accountNameInputCr.setText(null);
                gui.passwordInputCr.setText(null);
                gui.userName = null;
                gui.password = null;

                switch (gui.results)
                {
                    case 1:
                        gui.loginResult = "Account Created!\nPlease Login.";
                        gui.infoArea.setText(gui.loginResult);
                        gui.displayLogin();
                        break;
                    case 2:
                        gui.loginResult = "Account already exists."+ gui.password+"\nIf you do not remember your password.\n\nClick on reset password.";
                        gui.infoAreaCr.setText(gui.loginResult);
                        break;
                    case 3:
                        gui.loginResult = "Account not created.\n(Username & Password must each start with an upper case letter and may not be the same)";
                        gui.infoAreaCr.setText(gui.loginResult);
                        break;
                    default:
                        gui.loginResult = "ERROR: Unidentified error when creating account.";
                        gui.infoAreaCr.setText(gui.loginResult);
                        break;  
                }
            }
            catch (SQLException a)
            {
                System.out.println("Exception being thrown\n" + a );
            }
        }
        else
        {
            gui.accountNameInputCr.setText(null);
            gui.passwordInputCr.setText(null);
            gui.userName = null;
            gui.password = null;
            gui.infoAreaCr.setText("ERROR: Must enter an Account and Password");
        }
    }
    
    public String encryptPassword (String PWord)
    {
        try
        {
            MessageDigest encrypt = MessageDigest.getInstance("SHA-1");
            encrypt.reset();
            encrypt.update(PWord.getBytes());
            PWord = byteArrayToHexString(encrypt.digest());
        }
        catch(NoSuchAlgorithmException ex)
        {
            ex.printStackTrace();
        }
        System.out.println("Returning Password: " + PWord );
        return PWord;
    }
    
      public static String byteArrayToHexString (byte[] bArray)
      {
        StringBuilder sb = new StringBuilder(bArray.length * 2);
        for (int i = 0; i < bArray.length; i++)
        {
          int hex = bArray[i] & 0xff;
          if (hex < 16) 
          {
            sb.append('0');
          }
          sb.append(Integer.toHexString(hex));
        }
        return sb.toString().toUpperCase();
    }

    public void exit()
    {
        gui.dispose();
        System.exit(0);
    }    
   
}

