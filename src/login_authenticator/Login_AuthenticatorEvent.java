/**
 *
 * @author Jason, Shelley, Hossein
 */

package login_authenticator;

//////////////////////////////////////////////////
//  Imports
//////////////////////////////////////////////////
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.security.*;
import java.util.*;

public class Login_AuthenticatorEvent implements ActionListener, ItemListener, KeyListener, FocusListener, MouseListener, MouseMotionListener
{
    //////////////////////////////////////////////////
    //Class Member Variables
    //////////////////////////////////////////////////
    private Login_Authenticator gui;
    private String userName;
    private String userNameActive;
    private String password;
    private String passwordNew;
    private String passwordConfirm;
    private String loginResult;
    private String accountType;
    private int results;
    final private int RECONNECTTIMEOUT = 5;
    private boolean reachable;
    public String currPane= "LoginPane";
    public Stack<String> paneHistory= new Stack<>();
    private Point origPoint;

    //////////////////////////////////////////////////
    // Constructor
    //////////////////////////////////////////////////
    
    public Login_AuthenticatorEvent(Login_Authenticator input)
    {
        gui = input;
    }
    
    //////////////////////////////////////////////////
    //Overrriden Methods
    //////////////////////////////////////////////////
    
    //////////////////////////////////////////////////
    // Action Listener Overridden Methods
    //////////////////////////////////////////////////
    @Override
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
            case "Promote User":
                gui.displayPromote();
                populateRegUsers();
                break;
            case "Create Account":
                gui.infoAreaCr.setText(null);
                gui.displayCreate();
                break;
            case "Create":
                createAccount();
                break;
            case "Back to Login":
                paneHistory.pop();
                gui.infoArea.setText(null);
                gui.displayLogin();
                break;
            case "Back":
                System.out.println("In Back-Pane: " + paneHistory.peek());
                switch ((String)paneHistory.pop())
                {
                    case "LoginPane":
                        gui.displayLogin();
                        break;
                    case "ServerPane":
                        gui.displayServer();
                        break;
                    case "CreatePane":
                        gui.displayCreate();
                        break;
                    case "AdminPane":
                        gui.displayAdmin();
                        break;
                    case "UserPane":
                        gui.displayUser();
                        break;
                    case "UnlockPane":
                        gui.displayUnlock();
                        populateLockedUsers();
                        populateUnlockReasons();
                        break;
                    case "PromotePane":
                        gui.displayPromote();
                        populateRegUsers();
                        break;
                    case "ChangePWUPane":
                        gui.displayChangePWU();
                        break;
                    case "ChangePWAPane":
                        gui.displayChangePWA();
                        break;
                }
            break;
                
            case "Unlock Account":
                gui.displayUnlock();
                populateLockedUsers();
                populateUnlockReasons();
                break;
            case "Promote":
                promote();
                break;
            case "Logout":
                logout();
                break;
            case "Change Password":
                if (currPane.equals("UserPane"))
                {
                   System.out.println("Pane: " + currPane);
                   gui.displayChangePWU();
                }
                else
                    gui.displayChangePWA();
                break;
            case "Change":
                if (currPane.equals("ChangePWUPane"))
                   changePWU();
                else
                    changePWA();
                break;
            case "Try Again":
                gui.serverArea.setText("Connecting to database ....");
                tryAgain();
                break;
            case "Exit":
                exit();
                break;
        }
    }
    
    //////////////////////////////////////////////////
    // Focus Listener Overridden Methods
    //////////////////////////////////////////////////
    
    @Override
    public void focusLost(FocusEvent event)
    {
        Object theEvent = event.getSource();
        if (theEvent.equals(gui.accountNameInput))
        {
            userName = gui.accountNameInput.getText();
        }
        else if (theEvent.equals(gui.passwordInput))
        {
            password = String.valueOf(gui.passwordInput.getPassword());
        }
        else if (theEvent.equals(gui.accountNameInputCr))
        {
            userName = gui.accountNameInputCr.getText();
        }
        else if (theEvent.equals(gui.passwordInputCr))
        {
            password = String.valueOf(gui.passwordInputCr.getPassword());
        }
        else if (theEvent.equals(gui.passwordInputCurr))
        {
            password = String.valueOf(gui.passwordInputCurr.getPassword());
        }
        else if (theEvent.equals(gui.passwordInputNew))
        {
            passwordNew = String.valueOf(gui.passwordInputNew.getPassword());
        }
        else if (theEvent.equals(gui.passwordInputConfirm))
        {
            passwordConfirm = String.valueOf(gui.passwordInputConfirm.getPassword());
        }
        else if (theEvent.equals(gui.accountNameInputA))
        {
            userName = gui.accountNameInputA.getText();
        }
        else if (theEvent.equals(gui.passwordInputANew))
        {
            passwordNew = String.valueOf(gui.passwordInputANew.getPassword());
        }
        else if (theEvent.equals(gui.passwordInputAConfirm))
        {
            passwordConfirm = String.valueOf(gui.passwordInputAConfirm.getPassword());
        }
    }
    
    @Override
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
        else if (event.equals(gui.passwordInputCurr))
        {
            gui.passwordInputCurr.selectAll();
        }
        else if (event.equals(gui.passwordInputNew))
        {
            gui.passwordInputNew.selectAll();
        }
        else if (event.equals(gui.passwordInputConfirm))
        {
            gui.passwordInputConfirm.selectAll();
        }
        else if (event.equals(gui.accountNameInputA))
        {
            gui.accountNameInputA.selectAll();
        }
        else if (event.equals(gui.passwordInputANew))
        {
            gui.passwordInputANew.selectAll();
        }
        else if (event.equals(gui.passwordInputAConfirm))
        {
            gui.passwordInputAConfirm.selectAll();
        }
    }

    //////////////////////////////////////////////////
    // Key Listener Overridden Methods
    //////////////////////////////////////////////////
    
    @Override
    public void keyTyped (KeyEvent event)           
    {
        Object theEvent = event.getSource();

        if (event.getComponent() == gui.accountNameInput)
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                userName = gui.accountNameInput.getText();
                gui.passwordInput.requestFocusInWindow();
            }
        }
        else if (theEvent.equals(gui.passwordInput))
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                password = String.valueOf(gui.passwordInput.getPassword());
                login();
            }
        }
        else if (event.getComponent() == gui.accountNameInputCr)
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                userName = gui.accountNameInputCr.getText();
                gui.passwordInputCr.requestFocusInWindow();
            }
        }
        else if (theEvent.equals(gui.passwordInputCr))
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                password = String.valueOf(gui.passwordInputCr.getPassword());
                createAccount();
            }
        }
        else if (event.getComponent() == gui.passwordInputCurr)
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                password = String.valueOf(gui.passwordInputCurr.getPassword());
                gui.passwordInputNew.requestFocusInWindow();
            }
        }
        else if (event.getComponent() == gui.passwordInputNew)
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                passwordNew = String.valueOf(gui.passwordInputNew.getPassword());
                gui.passwordInputConfirm.requestFocusInWindow();
            }
        }
        else if (theEvent.equals(gui.passwordInputConfirm))
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                passwordConfirm = String.valueOf(gui.passwordInputConfirm.getPassword());
                changePWU();
            }
        }
        else if (event.getComponent() == gui.accountNameInputA)
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                userName = gui.accountNameInputA.getText();
                gui.passwordInputANew.requestFocusInWindow();
            }
        }
        else if (event.getComponent() == gui.passwordInputANew)
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                passwordNew = String.valueOf(gui.passwordInputANew.getPassword());
                gui.passwordInputAConfirm.requestFocusInWindow();
            }
        }
        else if (theEvent.equals(gui.passwordInputAConfirm))
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                passwordConfirm = String.valueOf(gui.passwordInputAConfirm.getPassword());
                changePWA();
            }
        }
        else if (event.getComponent() == gui.userListPromote)
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                promote();
            }
        }
        else if (theEvent.equals(gui.userListUnlock))
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                gui.reasonListUnlock.requestFocusInWindow();
            }
        }
        else if (theEvent.equals(gui.reasonListUnlock))
        {
            if (event.getKeyChar()== KeyEvent.VK_ENTER)
            {
                unlock();
            }
        }
    }


    //////////////////////////////////////////////////
    // Mouse Listener Overridden Methods
    //////////////////////////////////////////////////
    
    @Override
    public void mousePressed(MouseEvent e) 
    {
        Object source = e.getSource();

        if (source.equals(gui))
        {
            origPoint = e.getPoint();
            
        }
        else
        {
            System.out.println("ERROR: Not in the Window");
            e.consume();
        }
    }

    //////////////////////////////////////////////////
    // Mouse Movement Listener Overridden Methods
    //////////////////////////////////////////////////
    
    @Override
    public void mouseDragged(MouseEvent e) 
    {
        Object source = e.getSource();
        
        if (source.equals(gui))
        {            
             // get location of Window
            int thisX = (int)gui.getLocation().getX();
            int thisY = (int)gui.getLocation().getY();

            // Determine how much the mouse moved since the initial click
            int xMoved = (thisX + e.getX()) - (thisX + (int)origPoint.getX());
            int yMoved = (thisY + e.getY()) - (thisY + (int)origPoint.getY());

            // Move window to this position
            int X = thisX + xMoved;
            int Y = thisY + yMoved;
            gui.setLocation(X, Y);
            
        }
        else
        {
            System.out.println("ERROR: Not in the Window");
            e.consume();
        }    
    }
 
    //////////////////////////////////////////////////
    //Method that logs an account in
    //////////////////////////////////////////////////
    
    public void login()
    {   
        if(userName != null && password != null)
        {
            gui.infoArea.setText("Logging into Account...");
            try
            {
                if(validConn())
                {

                    gui.query = "begin Login_SP(?,?,?,?,?); end;";
                    CallableStatement callStmt = gui.conn.prepareCall(gui.query);

                    password = encryptPassword(password);

                    callStmt.setString(1, userName);
                    callStmt.setString(2, password);
                    callStmt.registerOutParameter(3, Types.INTEGER);
                    callStmt.registerOutParameter(4, Types.VARCHAR);
                    callStmt.registerOutParameter(5, Types.VARCHAR);
                    callStmt.execute();
                    results = callStmt.getInt(3);
                    loginResult = callStmt.getString(4);
                    accountType = callStmt.getString(5);

                    if (results == 1)
                    {
                        userNameActive = userName;
                    }

                    gui.accountNameInput.setText(null);
                    gui.passwordInput.setText(null);
                    userName = null;
                    password = null;
                    gui.infoArea.setText(loginResult);
                    gui.accountNameInput.requestFocus();

                    if (results == 1)
                    {
                        if (accountType.equalsIgnoreCase("User"))
                        {
                            gui.userLabel.setText("Account: " + userNameActive);
                            gui.displayUser();
                        }
                        else if (accountType.equalsIgnoreCase("Admin"))
                        {
                            gui.displayAdmin();
                        }
                        else
                        {
                            gui.infoArea.setText("Unrecognized Account Type");
                        }
                    }
                }
            }

            catch (SQLException a)
            {
                System.out.println("Exception being thrown\n" + a );
            }
        }
        else
        {
            gui.accountNameInput.setText(null);
            gui.passwordInput.setText(null);
            userName = null;
            password = null;
            gui.infoArea.setText("ERROR: Must enter an Account and Password");
            gui.accountNameInput.requestFocus();
        }
    }   
    
    //////////////////////////////////////////////////
    //Method that logs an account out
    //////////////////////////////////////////////////
    
    public void logout()
    {   
        try
        {
            reachable = validConn();

            gui.query = "begin Logout_SP(?,?); end;";
            CallableStatement callStmt = gui.conn.prepareCall(gui.query);

            callStmt.setString(1, userNameActive);
            callStmt.registerOutParameter(2, Types.INTEGER);
            callStmt.execute();
            results = callStmt.getInt(2);
            
            if (results == 0)
            {
                gui.infoArea.setText("You have already been logged out");
                gui.displayLogin();
            }
            else
            {
                gui.infoArea.setText("You have logged out successfully");
                gui.displayLogin();
            }
                    
        }

        catch (SQLException a)
        {
            System.out.println("Exception being thrown\n" + a );
        }
       
    
    } 
    
    //////////////////////////////////////////////////
    // Method that attempts to reconnect to the database
    //////////////////////////////////////////////////
    
    public void tryAgain()
    {           
        try
        {
            //creating connection to Oracle database using JDBC
            gui.conn = DriverManager.getConnection(gui.url,gui.props);

            switch (currPane)
            {
                case "LoginPane":
                    gui.displayLogin();
                    paneHistory.pop();
                    login();
                    break;
                case "CreatePane":
                    gui.displayCreate();
                    paneHistory.pop();
                    createAccount();
                    break;
                case "AdminPane":
                    gui.displayAdmin();
                    paneHistory.pop();
                    break;
                case "UserPane":
                    gui.displayUser();
                    paneHistory.pop();
                    break;
                case "UnlockPane":
                    gui.displayUnlock();
                    paneHistory.pop();
                    //populateLockedUsers();
                    populateUnlockReasons();
                    unlock();
                    break;
                case "PromotePane":
                    gui.displayPromote();
                    paneHistory.pop();
                    //populateRegUsers();
                    promote();
                    break;
                case "ChangePWUPane":
                    gui.displayChangePWU();
                    paneHistory.pop();
                    changePWU();
                    break;
                case "ChangePWAPane":
                    gui.displayChangePWA();
                    paneHistory.pop();
                    changePWA();
                    break;
            }
        }
            
        catch (SQLException a)
        {
            gui.query = "\nCannot connect to the database";
            gui.serverArea.setText(gui.query); 
            System.out.println("Exception being thrown\n" + a );
        }

    }
 
    //////////////////////////////////////////////////
    // Method that creates an account. All accounts are created at the user level initially.
    //////////////////////////////////////////////////
    public void createAccount()
    {
        if(userName != null && password != null &&
           userName != "" && password != "")
        {
            gui.infoAreaCr.setText("Creating Account...");
            try
            {
                if(validConn())
                {

                    results = 1;

                    if (userName.charAt(0) != Character.toUpperCase(userName.charAt(0)) ||
                        password.charAt(0) != Character.toUpperCase(password.charAt(0)))
                        results = 3;
                    if (userName.equalsIgnoreCase(password))
                        results = 3;

                    if (results == 1)
                    {
                        gui.query = "begin CREATEACCOUNT_SP(?,?,?); end;";
                        CallableStatement callStmt = gui.conn.prepareCall(gui.query);

                        password = encryptPassword(password);

                        callStmt.setString(1, userName);
                        callStmt.setString(2, password);
                        callStmt.registerOutParameter(3, Types.INTEGER);
                        callStmt.execute();
                        results = callStmt.getInt(3);
                    }

                    gui.accountNameInputCr.setText(null);
                    gui.passwordInputCr.setText(null);
                    gui.infoAreaCr.setText(null);
                    userName = null;
                    password = null;

                    switch (results)
                    {
                        case 1:
                            loginResult = "Account Created!\nPlease Login.";
                            gui.infoArea.setText(loginResult);
                            gui.displayLogin();
                            break;
                        case 2:
                            loginResult = "Account already exists.\nIf you do not remember your password.\n\nClick on reset password.";
                            gui.infoAreaCr.setText(loginResult);
                            gui.accountNameInputCr.requestFocus();
                            break;
                        case 3:
                            loginResult = "Account not created.\n(Username & Password must each start with an upper case letter and may not be the same)";
                            gui.infoAreaCr.setText(loginResult);
                            gui.accountNameInputCr.requestFocus();
                            break;
                        default:
                            loginResult = "ERROR: Unidentified error when creating account.";
                            gui.infoAreaCr.setText(loginResult);
                            gui.accountNameInputCr.requestFocus();
                            break;  
                    }
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
            userName = null;
            password = null;
            gui.infoAreaCr.setText("ERROR: Must enter an Account and Password");
            gui.accountNameInputCr.requestFocus();
        }
    }
    
    //////////////////////////////////////////////////
    // Method that allows a user to change their own password
    //////////////////////////////////////////////////
    public void changePWU()
    {
        if (password != null && passwordNew != null && passwordConfirm != null)
        {
            if(!passwordNew.equals(passwordConfirm))
            {
                gui.infoAreaChangePW.setText("ERROR: The new password fields do not match");
                gui.passwordInputCurr.requestFocusInWindow();
                gui.passwordInputCurr.setText(null);
                gui.passwordInputNew.setText(null);
                gui.passwordInputConfirm.setText(null);
                password = null;
                passwordNew = null;
                passwordConfirm = null;
            }
            else
            {
                if (passwordNew.charAt(0) == Character.toUpperCase(passwordNew.charAt(0)) && !passwordNew.equalsIgnoreCase(userNameActive))
                {
                    try
                    {
                        if(validConn())
                        {
                            System.out.println("Active Name: " + userNameActive);
                            gui.query = "SELECT PWord FROM Accounts WHERE Username = '"
                                        + userNameActive + "'";
                            ResultSet resultCur;
                            Statement stmt = gui.conn.createStatement();

                            password = encryptPassword(password);

                            resultCur = stmt.executeQuery(gui.query);
                            resultCur.next();
                            passwordConfirm = resultCur.getString("PWord");

                            if (!password.equals(passwordConfirm))
                            {
                                gui.infoAreaChangePW.setText("Current Password is Incorrect\nPlease re-enter");
                                gui.passwordInputCurr.requestFocusInWindow();
                                gui.passwordInputCurr.setText(null);
                                gui.passwordInputNew.setText(null);
                                gui.passwordInputConfirm.setText(null);
                                password = null;
                                passwordNew = null;
                                passwordConfirm = null;
                            }
                            else
                            {
                                passwordNew = encryptPassword(passwordNew);
                                gui.query = "UPDATE Accounts SET PWord = '"
                                        + passwordNew + "' WHERE Username = '"
                                        + userNameActive +"'";
                                stmt.executeUpdate(gui.query);
                                gui.passwordInputCurr.setText(null);
                                gui.passwordInputNew.setText(null);
                                gui.passwordInputConfirm.setText(null);
                                password = null;
                                passwordNew = null;
                                passwordConfirm = null;
                                gui.infoAreaChangePW.setText("Password Change Successful!");
                            }
                            resultCur.close();
                        }
                    }
                    catch(SQLException ex)
                    {
                        System.out.println("Exception being thrown\n" + ex );
                    }
                }
                else
                {
                    gui.infoAreaChangePW.setText("ERROR: Password must start with an upper case letter and may not match the Username");
                    gui.passwordInputCurr.setText(null);
                    gui.passwordInputNew.setText(null);
                    gui.passwordInputConfirm.setText(null);
                    password = null;
                    passwordNew = null;
                    passwordConfirm = null;   
                }
            }
        }
        else
        {
            gui.infoAreaChangePW.setText("ERROR: Must fill in all fields");
            gui.passwordInputCurr.setText(null);
            gui.passwordInputNew.setText(null);
            gui.passwordInputConfirm.setText(null);
            password = null;
            passwordNew = null;
            passwordConfirm = null;
            gui.passwordInputCurr.requestFocusInWindow();
        }

    }
    
    //////////////////////////////////////////////////
    // Method to allow an Admin to change a specifiec user's password
    //////////////////////////////////////////////////
    public void changePWA()
    {
        if (userName != null && passwordNew != null && passwordConfirm != null)
        {
            if(!passwordNew.equals(passwordConfirm))
            {
                gui.infoAreaChangePWA.setText("ERROR: The new password fields do not match");
                gui.accountNameInputA.requestFocusInWindow();
                gui.accountNameInputA.setText(null);
                gui.passwordInputANew.setText(null);
                gui.passwordInputAConfirm.setText(null);
                userName = null;
                passwordNew = null;
                passwordConfirm = null;
            }
            else
            {
                if (passwordNew.charAt(0) == Character.toUpperCase(passwordNew.charAt(0)) && !passwordNew.equalsIgnoreCase(userName))
                {
                    try
                    {
                        if(validConn())
                        {
                            
                            gui.query = "SELECT UserName FROM Accounts WHERE Username = '"
                                        + userName + "'";
                            ResultSet resultCur;
                            Statement stmt = gui.conn.createStatement();

                            resultCur = stmt.executeQuery(gui.query);
                            resultCur.next();
                            
                            if (resultCur.getRow() != 0)
                            {
                                passwordNew = encryptPassword(passwordNew);
                                gui.query = "UPDATE Accounts SET PWord = '"
                                        + passwordNew + "' WHERE Username = '"
                                        + userName +"'";
                                stmt.executeUpdate(gui.query);
                                gui.accountNameInputA.setText(null);
                                gui.passwordInputANew.setText(null);
                                gui.passwordInputAConfirm.setText(null);
                                userName = null;
                                passwordNew = null;
                                passwordConfirm = null;
                                gui.infoAreaChangePWA.setText("Password Change Successful!");
                                gui.accountNameInputA.requestFocusInWindow();
                            }
                            else
                            {
                                gui.accountNameInputA.setText(null);
                                gui.passwordInputANew.setText(null);
                                gui.passwordInputAConfirm.setText(null);
                                userName = null;
                                passwordNew = null;
                                passwordConfirm = null;
                                gui.infoAreaChangePWA.setText("Account does not exist");
                                gui.accountNameInputA.requestFocusInWindow();
                            }
                            resultCur.close();
                        }
                    }
                    catch(SQLException ex)
                    {
                        System.out.println("Exception being thrown\n" + ex );
                    }
                }
                else
                {
                    gui.infoAreaChangePWA.setText("ERROR: Password must start with an upper case letter and may not match the Username");
                    gui.accountNameInputA.requestFocusInWindow();
                    gui.accountNameInputA.setText(null);
                    gui.passwordInputANew.setText(null);
                    gui.passwordInputAConfirm.setText(null);
                    userName = null;
                    passwordNew = null;
                    passwordConfirm = null; 
                }
            }
        }
        else
        {
            gui.infoAreaChangePWA.setText("ERROR: Must fill in all fields");
            gui.accountNameInputA.requestFocusInWindow();
            gui.accountNameInputA.setText(null);
            gui.passwordInputANew.setText(null);
            gui.passwordInputAConfirm.setText(null);
            userName = null;
            passwordNew = null;
            passwordConfirm = null;
        }

    }
     
    //////////////////////////////////////////////////
    //Method to allow an Admin to unlock an account
    //////////////////////////////////////////////////
    
    public void unlock()
    {
        int AccId;
        int ReasonId;
        int ValidID;
        int ValidReason;
        String reasonName;

        if(gui.userListUnlock.getSelectedItem() != null &&
                gui.userListUnlock.getSelectedItem() != "" &&
                gui.reasonListUnlock.getSelectedItem() != null &&
                gui.reasonListUnlock.getSelectedItem() != "")
        {
            userName = String.valueOf(gui.userListUnlock.getSelectedItem());
            reasonName = String.valueOf(gui.reasonListUnlock.getSelectedItem());
            
            gui.infoAreaUnlock.setText("Unlocking Account...");
            try
            {
                if(validConn())
                {
                    gui.query = "SELECT COUNT(ID) FROM ACCOUNTS WHERE USERNAME = '"
                        + userName + "'";
                    ResultSet resultCur;
                    Statement stmt = gui.conn.createStatement();
                    resultCur = stmt.executeQuery(gui.query);
                    resultCur.next();
                    ValidID = resultCur.getInt(1);
                    resultCur.close();
                    
                    if(ValidID == 0)
                    {
                        results = 2;
                    }
                    else
                    {
                        gui.query = "SELECT ID FROM ACCOUNTS WHERE USERNAME = '"
                            + userName + "'";
                        stmt = gui.conn.createStatement();
                        resultCur = stmt.executeQuery(gui.query);
                        resultCur.next();
                        AccId = resultCur.getInt(1);
                        
                        gui.query = "SELECT COUNT(ID) FROM EVENTREASONS WHERE REASONNAME = '"
                            + reasonName + "' AND REASONTYPE = 'U'";
                        stmt = gui.conn.createStatement();
                        resultCur = stmt.executeQuery(gui.query);
                        resultCur.next();
                        ValidReason = resultCur.getInt(1);
                        resultCur.close();

                        if(ValidReason == 0)
                        {
                            results = 4;
                        }
                        else
                        {
                            gui.query = "SELECT ID FROM EVENTREASONS WHERE REASONNAME = '"
                                + reasonName + "'";
                            stmt = gui.conn.createStatement();
                            resultCur = stmt.executeQuery(gui.query);
                            resultCur.next();
                            ReasonId = resultCur.getInt(1);
                            resultCur.close();

                            results = 1;

                            gui.query = "begin UNLOCK_SP(?,?,?); end;";
                            CallableStatement callStmt = gui.conn.prepareCall(gui.query);

                            callStmt.setInt(1, AccId);
                            callStmt.setInt(2, ReasonId);
                            callStmt.registerOutParameter(3, Types.INTEGER);
                            callStmt.execute();
                            results = callStmt.getInt(3);

                            gui.infoAreaUnlock.setText(null);
                            userName = null;
                        }
                    }
                    
                    switch (results)
                    {
                        case 1:
                            loginResult = "Account Unlocked.";
                            gui.infoAreaUnlock.setText(loginResult);
                            populateLockedUsers();
                            break;
                        case 2:
                            loginResult = "Account does not exist.";
                            gui.infoAreaUnlock.setText(loginResult);
                            // gui.accountNameInputUnlock.requestFocus();
                            break;
                        case 3:
                            loginResult = "Account is already unlocked; no need to unlock it.";
                            gui.infoAreaUnlock.setText(loginResult);
                            // gui.accountNameInputUnlock.requestFocus();
                            break;
                        case 4:
                            loginResult = "That is not a valid unlock reason.";
                            gui.infoAreaUnlock.setText(loginResult);
                            // gui.accountNameInputUnlock.requestFocus();
                            break;
                        default:
                            loginResult = "ERROR: Unidentified error when unlocking account.";
                            gui.infoAreaUnlock.setText(loginResult);
                            // gui.accountNameInputUnlock.requestFocus();
                            break;  
                    }
                }
            }
            catch (SQLException a)
            {
                System.out.println("Exception being thrown\n" + a );
            }
        }
        else
        {
            // gui.accountNameInputUnlock.setText(null);
            userName = null;
            gui.infoAreaUnlock.setText("ERROR: Must enter a username.");
            // gui.accountNameInputUnlock.requestFocus();
        }
    }
    
    //////////////////////////////////////////////////
    //Method that populates list of locked users
    //////////////////////////////////////////////////
    
    public void populateLockedUsers()
    {
        try
        {
            if(validConn())
            {
                gui.userListUnlock.removeAllItems();
                
                gui.query = "SELECT USERNAME FROM ACCOUNTS WHERE LOCKSTATUS = 'Y'";
                ResultSet resultCur;
                Statement stmt = gui.conn.createStatement();
                resultCur = stmt.executeQuery(gui.query);
                
                while(resultCur.next())
                {
                    gui.userListUnlock.addItem(resultCur.getString(1));
                }
                resultCur.close();
            }
        }
        catch (SQLException a)
        {
            System.out.println("Exception being thrown\n" + a );
        }
    }
    
    //////////////////////////////////////////////////
    //Method that populates list of unlock reasons
    //////////////////////////////////////////////////
    
    public void populateUnlockReasons()
    {
        try
        {
            if(validConn())
            {
                gui.reasonListUnlock.removeAllItems();
                
                gui.query = "SELECT REASONNAME FROM EVENTREASONS WHERE REASONTYPE = 'U'";
                ResultSet resultCur;
                Statement stmt = gui.conn.createStatement();
                resultCur = stmt.executeQuery(gui.query);
                
                while(resultCur.next())
                {
                    if (!resultCur.getString(1).trim().toUpperCase().equals("CREATION"))
                    {
                        gui.reasonListUnlock.addItem(resultCur.getString(1));
                    }
                }
                resultCur.close();
            }
        }
        catch (SQLException a)
        {
            System.out.println("Exception being thrown\n" + a );
        }
    }
    
    public void promote()
    {
        int AccId;
        int ValidID;

        if(gui.userListPromote.getSelectedItem() != null &&
                gui.userListPromote.getSelectedItem() != "")
        {
            userName = String.valueOf(gui.userListPromote.getSelectedItem());
            
            gui.infoAreaPromote.setText("Promoting User...");
            try
            {
                if(validConn())
                {
                    gui.query = "SELECT COUNT(ID) FROM ACCOUNTS WHERE USERNAME = '"
                        + userName + "'";
                    ResultSet resultCur;
                    Statement stmt = gui.conn.createStatement();
                    resultCur = stmt.executeQuery(gui.query);
                    resultCur.next();
                    ValidID = resultCur.getInt(1);
                    resultCur.close();
                    
                    if(ValidID == 0)
                    {
                        results = 2;
                    }
                    else
                    {                       
                        gui.query = "SELECT ID FROM ACCOUNTS WHERE USERNAME = '"
                            + userName + "'";
                        stmt = gui.conn.createStatement();
                        resultCur = stmt.executeQuery(gui.query);
                        resultCur.next();
                        AccId = resultCur.getInt(1);
                        resultCur.close();

                        results = 1;

                        gui.query = "UPDATE ACCOUNTS SET ACCOUNTTYPES_ID = 1 "
                                + "WHERE USERNAME = '" + userName + "'";
                        stmt = gui.conn.createStatement();
                        resultCur = stmt.executeQuery(gui.query);
                        resultCur.close();

                        gui.infoAreaPromote.setText(null);
                    }
                    
                    switch (results)
                    {
                        case 1:
                            loginResult = "Promoted " + userName + " to Admin.";
                            gui.infoAreaPromote.setText(loginResult);
                            populateRegUsers();
                            userName = null;
                            break;
                        case 2:
                            loginResult = "Account does not exist.";
                            gui.infoAreaPromote.setText(loginResult);
                            break;
                        default:
                            loginResult = "ERROR: Unidentified error when promoting account.";
                            gui.infoAreaPromote.setText(loginResult);
                            break;  
                    }
                }
            }
            catch (SQLException a)
            {
                System.out.println("Exception being thrown\n" + a );
            }
        }
        else
        {
            userName = null;
            gui.infoAreaPromote.setText("ERROR: Must enter a username.");
        }
    }
    
    //////////////////////////////////////////////////
    //Method that populates list of regular users
    //////////////////////////////////////////////////
    
    public void populateRegUsers()
    {
        try
        {
            if(validConn())
            {
                gui.userListPromote.removeAllItems();
                
                gui.query = "SELECT ACCOUNTS.USERNAME FROM ACCOUNTS INNER JOIN ACCOUNTTYPES "
                            + "ON ACCOUNTS.ACCOUNTTYPES_ID = ACCOUNTTYPES.ID "
                            + "WHERE ACCOUNTTYPES.TYPENAME = 'USER'";
                ResultSet resultCur;
                Statement stmt = gui.conn.createStatement();
                resultCur = stmt.executeQuery(gui.query);
                
                while(resultCur.next())
                {
                    gui.userListPromote.addItem(resultCur.getString(1));
                }
                resultCur.close();
            }
        }
        catch (SQLException a)
        {
            System.out.println("Exception being thrown\n" + a );
        }
    }
    
    //////////////////////////////////////////////////
    //Method that validates that the connection to the database is good
    //////////////////////////////////////////////////
    
    public boolean validConn ()
    {
        try
        {
            reachable = gui.conn.isValid(RECONNECTTIMEOUT);
            if (reachable == false)
                {
                    gui.serverArea.setText("\nCannot connect to the database");
                    gui.displayServer();
                    gui.repaint();
                }
        }
        catch( SQLException a)
        {
            System.out.println("Exception being thrown\n" + a );
        }
        return reachable;
    }
    
    //////////////////////////////////////////////////
    // Method that encrypts passwords
    //////////////////////////////////////////////////
    
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
        return PWord;
    }
    
    //////////////////////////////////////////////////
    // Method that transforms the encryption into a Hex Char String
    //////////////////////////////////////////////////
    
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

    //////////////////////////////////////////////////
    // Method that exits the program
    //////////////////////////////////////////////////
    
    public void exit()
    {
        gui.dispose();
        System.exit(0);
    }    
    
    //////////////////////////////////////////////////
    //Unused Manditory Override Methods
    //////////////////////////////////////////////////
    
        /////////////////////////////////////////////
        //Unused Item Listener Methods
        /////////////////////////////////////////////
    
    @Override
    public void itemStateChanged(ItemEvent event)
    {
    }
    
        /////////////////////////////////////////////
        //Unused Key Listener Methods
        /////////////////////////////////////////////
    
    @Override
    public void keyPressed (KeyEvent event)
    {
        
    }
    
    @Override
    public void keyReleased (KeyEvent event)
    {
        
    }
    
        /////////////////////////////////////////////
        //Unused Mouse Listener Methods 
        /////////////////////////////////////////////
    
    @Override
    public void mouseClicked(MouseEvent e) 
    {

    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        
    }
    
        ////////////////////////////////////////////
        //Unused Mouse Movement Listener Methods
        ////////////////////////////////////////////
    
    @Override
    public void mouseEntered(MouseEvent e) 
    {

    }

    @Override
    public void mouseExited(MouseEvent e) 
    {

    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {

    }
}


