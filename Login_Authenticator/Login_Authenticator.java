/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login_authenticator;
import java.sql.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;


/**
 *
 * @author Jason
 */
public class Login_Authenticator extends JFrame 
{
    Login_AuthenticatorEvent loginEvent = new Login_AuthenticatorEvent(this);
 
    //Class Member Variables 
    String userName;
    String userNameActive;
    String password;
    String loginResult;
    int results;
    String query;
    String url;
    int programState=1;
    boolean reachable;
    CardLayout mainLayout;
    String currPane= "LoginPane";
    Stack paneHistory= new Stack();
        
    //Properties for creating connection to Oracle database
    Properties props = new Properties();
    Connection conn;
    Statement stmt;
    
    //Components for Login Window
        //Labels and Background
    ImageIcon backgroundLogin = new ImageIcon("background.jpg");
    JLabel masterPaneLogin = new JLabel(backgroundLogin);
    JLabel titleLabel = new JLabel("Account Login");
    JLabel accountNameLabel = new JLabel ("Account:");
    JLabel passwordLabel = new JLabel ("Password:  ");
        //Input fields
    JTextField accountNameInput = new JTextField ();
    JPasswordField passwordInput = new JPasswordField ();
        //Buttons
    JButton login = new JButton("Login");
    JButton exit = new JButton ("Exit");
    JButton createAccount = new JButton("Create Account");    
        //Display Areas
    JTextArea infoArea = new JTextArea ();
    
    //Components for Server Connection
        //Labels and Background
    ImageIcon backgroundServer = new ImageIcon("background.jpg");
    JLabel masterPaneServer = new JLabel(backgroundServer);
    JLabel serverLabel = new JLabel("Login Server Unavailable");
        //Buttons
    JButton tryAgain  = new JButton ("Try Again");
    JButton serverExit = new JButton ("Exit");
        //Display Areas
    JTextArea serverArea = new JTextArea ();
    
    //Components for Create Account
        //Labels and Background
    ImageIcon backgroundCreate = new ImageIcon("background.jpg");
    JLabel masterPaneCreate = new JLabel(backgroundCreate);
    JLabel createLabel = new JLabel("Account Creation");
    JLabel accountNameLabelCr = new JLabel ("Account:");
    JLabel passwordLabelCr = new JLabel ("Password:  ");
        //Input fields
    JTextField accountNameInputCr = new JTextField ();
    JPasswordField passwordInputCr = new JPasswordField ();
        //Buttons
    JButton create  = new JButton ("Create");
    JButton loginPage = new JButton ("Back to Login");
    JButton createExit = new JButton ("Exit");
        //Display Areas
    JTextArea infoAreaCr = new JTextArea ();
    
    //Components for Admin Pane
        //Labels and Background
    ImageIcon backgroundAdmin = new ImageIcon("background2.jpg");
    JLabel masterPaneAdmin = new JLabel(backgroundAdmin);
    JLabel adminLabel = new JLabel("Account Administration");
        //Buttons
    JButton unlockAdmin  = new JButton ("Unlock Account");
    JButton resetPasswordAdmin = new JButton ("Password Reset");
    JButton logoutAdmin = new JButton ("Logout");
    
    //Components for User Pane
        //Labels and Background
    ImageIcon backgroundUser = new ImageIcon("background2.jpg");
    JLabel masterPaneUser = new JLabel(backgroundUser);
    JLabel userLabel = new JLabel("Account ****");
        //Buttons
    JButton resetPasswordUser = new JButton ("Password Reset");
    JButton logoutUser = new JButton ("Logout");
    
    //Components for Unlock Pane
        //Labels and Background
    ImageIcon backgroundUnlock = new ImageIcon("background2.jpg");
    JLabel masterPaneUnlock = new JLabel(backgroundUnlock);
    JLabel unlockLabel = new JLabel("Account Lock Management");
    JLabel accountNameLabelUnlock = new JLabel ("Account:");
        //Input fields
    JTextField accountNameInputUnlock = new JTextField ();
        //Buttons
    JButton unlock  = new JButton ("Unlock");
    JButton prevPageUnlock = new JButton ("Back to Admin");
        //Display Areas
    JTextArea infoAreaUnlock = new JTextArea ();

    //Components for Password Reset Window
        //Labels and Background
    ImageIcon backgroundReset = new ImageIcon("background2.jpg");
    JLabel masterPaneReset = new JLabel(backgroundReset);
    JLabel resetLabel = new JLabel("Reset Password");
    JLabel passwordLabelCurr = new JLabel ("Current Password:");
    JLabel passwordLabelNew = new JLabel ("New Password:");
    JLabel passwordLabelConfirm = new JLabel ("Confirm Password:");
        //Input fields
    JPasswordField passwordInputCurr = new JPasswordField ();
    JPasswordField passwordInputNew = new JPasswordField ();
    JPasswordField passwordInputConfirm = new JPasswordField ();
        //Buttons
    JButton resetPassword = new JButton("Reset");
    JButton prevPageReset = new JButton ("Back");    
        //Display Areas
    JTextArea infoAreaReset = new JTextArea ();
    
    //Constructor
    public Login_Authenticator()  throws SQLException
    { 
        
        super();
        
        //Setup for Connection with the Database
        url = "jdbc:oracle:thin:@cncsidb01.msudenver.edu:1521:DB01";
        props.setProperty("user", "S900821204");
        props.setProperty("password", "Shadowz#2");        
     
        try
        {
            //creating connection to Oracle database using JDBC
            conn = DriverManager.getConnection(url,props);
            stmt = conn.createStatement();
        }
        catch(SQLException a)
        {
            System.out.println("Exception being thrown\n" + a );
            programState = 2;
        }
       
        //Setup the Parent Panel
        this.setLocationByPlatform(true);
        setUndecorated(false);
        setSize(490,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainLayout = new CardLayout();
        setLayout(mainLayout);
        
        //Create a font to use in Headers
        Font largeFont = new Font("Dialog", Font.BOLD, 18);
        
        //Add Listeners
            //Input Pane Listeners
        accountNameInput.addKeyListener(loginEvent);
        accountNameInput.addFocusListener(loginEvent);
        passwordInput.addKeyListener(loginEvent);
        passwordInput.addFocusListener(loginEvent);
        login.addActionListener(loginEvent);
        exit.addActionListener(loginEvent);
        createAccount.addActionListener(loginEvent);

            //Server Pane Listeners
        tryAgain.addActionListener(loginEvent);
        serverExit.addActionListener(loginEvent);
        
            //Create Pane Listeners
        accountNameInputCr.addKeyListener(loginEvent);
        accountNameInputCr.addFocusListener(loginEvent);
        passwordInputCr.addKeyListener(loginEvent);
        passwordInputCr.addFocusListener(loginEvent); 
        loginPage.addActionListener(loginEvent);
        createExit.addActionListener(loginEvent);
        create.addActionListener(loginEvent);
        
            //Admin Pane Listeners
        unlockAdmin.addActionListener(loginEvent);
        resetPasswordAdmin.addActionListener(loginEvent);
        logoutAdmin.addActionListener(loginEvent);
        
            //User Pane Listeners
        resetPasswordUser.addActionListener(loginEvent);
        logoutUser.addActionListener(loginEvent);
        
            //Unlock Pane Listeners
        accountNameInputUnlock.addKeyListener(loginEvent);
        accountNameInputUnlock.addFocusListener(loginEvent);
        unlock.addActionListener(loginEvent);
        prevPageUnlock.addActionListener(loginEvent);
        
            //Reset Pane Listeners
        passwordInputCurr.addKeyListener(loginEvent);
        passwordInputCurr.addFocusListener(loginEvent);
        passwordInputNew.addKeyListener(loginEvent);
        passwordInputNew.addFocusListener(loginEvent);
        passwordInputConfirm.addKeyListener(loginEvent);
        passwordInputConfirm.addFocusListener(loginEvent);
        resetPassword.addActionListener(loginEvent);
        prevPageReset.addActionListener(loginEvent);
        
        //Setting up the Login Pane
        GridBagLayout masterLayoutLogin = new GridBagLayout();
        masterPaneLogin.setLayout(masterLayoutLogin);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; 
        c.weighty = 0;
        
            //Adding components to Login Pane
        c.gridx=0;
        c.gridy=0;
        c.gridheight=1;
        c.gridwidth=3;
        c.insets = new Insets(110,85,20,20);
        titleLabel.setFont(largeFont);
        titleLabel.setOpaque(false);
        titleLabel.setForeground(Color.white);
        masterPaneLogin.add(titleLabel, c);
                
        c.gridx=0;
        c.gridy=1;
        c.gridheight=1;
        c.gridwidth=1;
        c.insets = new Insets(0,0,10,0);
        c.ipadx=0;
        accountNameLabel.setOpaque(false);
        accountNameLabel.setForeground(Color.white);
        masterPaneLogin.add(accountNameLabel, c);
        
        c.gridx=1;
        c.gridy=1;
        c.gridheight=1;
        c.gridwidth=2;
        c.insets = new Insets(0,0,10,0);
        accountNameInput.setOpaque(true);
        accountNameInput.setForeground(Color.black);
        //accountNameInput.setText();
        accountNameInput.setCaretColor(Color.black);
        masterPaneLogin.add(accountNameInput, c);
                
        c.gridx=0;
        c.gridy=2;
        c.gridheight=1;
        c.gridwidth=1;
        c.insets = new Insets(0,0,0,0);
        c.ipadx = 0;
        passwordLabel.setOpaque(false);
        passwordLabel.setForeground(Color.white);
        masterPaneLogin.add(passwordLabel, c);
                
        c.gridx=1;
        c.gridy=2;
        c.gridheight=1;
        c.gridwidth=2;
        c.insets = new Insets(0,0,0,0);
        passwordInput.setOpaque(true);
        passwordInput.setForeground(Color.black);
        passwordInput.setCaretColor(Color.black);
        masterPaneLogin.add(passwordInput, c);
                        
        c.gridx=1;
        c.gridy=4;
        c.insets = new Insets(25,45,0,20);
        c.gridheight=1;
        c.gridwidth=1;
        c.weighty=0;
        c.fill= GridBagConstraints.NONE;
        login.setOpaque(true);
        masterPaneLogin.add(login, c);
        
        c.gridx=2;
        c.gridy=4;
        c.insets = new Insets(25,20,0,20);
        c.gridheight=1;
        c.gridwidth=1;
        exit.setOpaque(true);
        masterPaneLogin.add(exit, c);
                
        c.gridx=1;
        c.gridy=5;
        c.weighty = 1;
        c.gridheight=1;
        c.gridwidth=2;
        c.fill= GridBagConstraints.BOTH;
        c.ipady = 150;
        c.insets = new Insets(10,70,0,0);
        infoArea.setOpaque(false);
        infoArea.setForeground(Color.white);
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        masterPaneLogin.add(infoArea, c);
                        
        c.gridx=0;
        c.gridy=7;
        c.ipady = 0;
        c.insets = new Insets(0,0,30,30);
        c.gridheight=1;
        c.gridwidth=3;
        c.weighty = 0;
        c.fill = GridBagConstraints.EAST;
        createAccount.setOpaque(true);
        masterPaneLogin.add(createAccount, c);
       
            //Adding Login Pane to the CardLayout
        mainLayout.addLayoutComponent(masterPaneLogin, "LoginPane");
            //Adding Login Pane to the Parent Panel
        add(masterPaneLogin);
        
        //Building the Pane for Server
        GridBagLayout masterLayoutServer = new GridBagLayout();
        masterPaneServer.setLayout(masterLayoutServer);
        GridBagConstraints cServ = new GridBagConstraints();
        cServ.fill = GridBagConstraints.BOTH;  
        
            //Adding Components to Server Pane
        cServ.gridx=0;
        cServ.gridy=0;
        cServ.gridheight=1;
        cServ.gridwidth=3;
        cServ.insets = new Insets(110,60,20,20);
        serverLabel.setFont(largeFont);
        serverLabel.setOpaque(false);
        serverLabel.setForeground(Color.white);
        masterPaneServer.add(serverLabel, cServ);
        
        cServ.gridx=1;
        cServ.gridy=1;
        cServ.insets = new Insets(70,120,0,0);
        cServ.gridheight=1;
        cServ.gridwidth=1;
        cServ.weighty=0;
        cServ.fill= GridBagConstraints.NONE;
        tryAgain.setOpaque(true);
        masterPaneServer.add(tryAgain, cServ);
        
        cServ.gridx=2;
        cServ.gridy=1;
        cServ.insets = new Insets(70,20,0,30);
        cServ.gridheight=1;
        cServ.gridwidth=1;
        serverExit.setOpaque(true);
        masterPaneServer.add(serverExit, cServ);
                
        cServ.gridx=1;
        cServ.gridy=2;
        cServ.weighty = 1;
        cServ.gridheight=1;
        cServ.gridwidth=2;
        cServ.fill= GridBagConstraints.BOTH;
        cServ.insets = new Insets(20,130,0,0);
        serverArea.setOpaque(false);
        serverArea.setForeground(Color.white);
        serverArea.setEditable(false);
        serverArea.setLineWrap(true);
        serverArea.setWrapStyleWord(true);
        masterPaneServer.add(serverArea, cServ);

            //Adding Server Pane to CardLayout
        mainLayout.addLayoutComponent(masterPaneServer, "ServerPane");
            //Adding Server Pane to Parent Panel
        add(masterPaneServer);
        
        //Building the Pane for Create
        GridBagLayout masterLayoutCreate = new GridBagLayout();
        masterPaneCreate.setLayout(masterLayoutCreate);
        GridBagConstraints cCr = new GridBagConstraints();
        cCr.fill = GridBagConstraints.BOTH; 
               
            //Adding Components to Create Pane
        cCr.gridx=0;
        cCr.gridy=0;
        cCr.weighty = 0;
        cCr.gridheight=1;
        cCr.gridwidth=3;
        cCr.insets = new Insets(110,85,20,20);
        createLabel.setFont(largeFont);
        createLabel.setOpaque(false);
        createLabel.setForeground(Color.white);
        masterPaneCreate.add(createLabel, cCr);
        
        cCr.gridx=0;
        cCr.gridy=1;
        cCr.gridheight=1;
        cCr.gridwidth=1;
        cCr.insets = new Insets(0,0,10,0);
        cCr.ipadx=0;
        accountNameLabelCr.setOpaque(false);
        accountNameLabelCr.setForeground(Color.white);
        masterPaneCreate.add(accountNameLabelCr, cCr);
        
        cCr.gridx=1;
        cCr.gridy=1;
        cCr.gridheight=1;
        cCr.gridwidth=2;
        cCr.insets = new Insets(0,0,10,0);
        accountNameInputCr.setOpaque(true);
        accountNameInputCr.setForeground(Color.black);
        accountNameInputCr.setCaretColor(Color.black);
        masterPaneCreate.add(accountNameInputCr, cCr);
                
        cCr.gridx=0;
        cCr.gridy=2;
        cCr.gridheight=1;
        cCr.gridwidth=1;
        cCr.insets = new Insets(0,0,0,0);
        cCr.ipadx = 0;
        passwordLabelCr.setOpaque(false);
        passwordLabelCr.setForeground(Color.white);
        masterPaneCreate.add(passwordLabelCr, cCr);
                
        cCr.gridx=1;
        cCr.gridy=2;
        cCr.gridheight=1;
        cCr.gridwidth=2;
        cCr.insets = new Insets(0,0,0,0);
        passwordInputCr.setOpaque(true);
        passwordInputCr.setForeground(Color.black);
        passwordInputCr.setCaretColor(Color.black);
        masterPaneCreate.add(passwordInputCr, cCr);
        
        cCr.gridx=1;
        cCr.gridy=4;
        cCr.insets = new Insets(25,45,0,20);
        cCr.gridheight=1;
        cCr.gridwidth=1;
        cCr.weighty=0;
        cCr.fill= GridBagConstraints.NONE;
        create.setOpaque(true);
        masterPaneCreate.add(create, cCr);
                
        cCr.gridx=2;
        cCr.gridy=4;
        cCr.insets = new Insets(25,13,0,20);
        cCr.gridheight=1;
        cCr.gridwidth=1;
        createExit.setOpaque(true);
        masterPaneCreate.add(createExit, cCr);
                        
        cCr.gridx=1;
        cCr.gridy=5;
        cCr.weighty = 1;
        cCr.gridheight=1;
        cCr.gridwidth=2;
        cCr.fill= GridBagConstraints.BOTH;
        cCr.weighty = 1;
        cCr.ipady = 150;
        cCr.insets = new Insets(10,70,0,0);
        infoAreaCr.setOpaque(false);
        infoAreaCr.setForeground(Color.white);
        infoAreaCr.setEditable(false);
        infoAreaCr.setLineWrap(true);
        infoAreaCr.setWrapStyleWord(true);
        masterPaneCreate.add(infoAreaCr, cCr);

        cCr.gridx=0;
        cCr.gridy=6;
        cCr.insets = new Insets(0,0,30,30);
        cCr.gridheight=1;
        cCr.gridwidth=3;
        cCr.weighty = 0;
        cCr.ipady = 0;
        cCr.fill = GridBagConstraints.NONE;
        loginPage.setOpaque(true);
        masterPaneCreate.add(loginPage, cCr);
        
            //Add Create Pane to CardLayout
        mainLayout.addLayoutComponent(masterPaneCreate, "CreatePane");
            //Add Create Pane to Parent Panel
        add(masterPaneCreate);
        
        //Building the Pane for Admin
        GridBagLayout masterLayoutAdmin = new GridBagLayout();
        masterPaneAdmin.setLayout(masterLayoutAdmin);
        GridBagConstraints cAdmin = new GridBagConstraints();
        cAdmin.fill = GridBagConstraints.BOTH;  
        
            //Adding Components to Admin Pane
        cAdmin.gridx=0;
        cAdmin.gridy=0;
        cAdmin.gridheight=1;
        cAdmin.gridwidth=3;
        cAdmin.insets = new Insets(110,60,20,20);
        adminLabel.setFont(largeFont);
        adminLabel.setOpaque(false);
        adminLabel.setForeground(Color.white);
        masterPaneAdmin.add(adminLabel, cAdmin);
        
        cAdmin.gridx=1;
        cAdmin.gridy=1;
        cAdmin.insets = new Insets(70,100,0,0);
        cAdmin.gridheight=1;
        cAdmin.gridwidth=1;
        cAdmin.weighty=0;
        cAdmin.fill= GridBagConstraints.NONE;
        unlockAdmin.setOpaque(true);
        masterPaneAdmin.add(unlockAdmin, cAdmin);
        
        cAdmin.gridx=1;
        cAdmin.gridy=2;
        cAdmin.insets = new Insets(20,100,0,0);
        cAdmin.gridheight=1;
        cAdmin.gridwidth=1;
        resetPasswordAdmin.setOpaque(true);
        masterPaneAdmin.add(resetPasswordAdmin, cAdmin);
        
        cAdmin.gridx=1;
        cAdmin.gridy=3;
        cAdmin.insets = new Insets(0,100,30,0);
        cAdmin.gridheight=1;
        cAdmin.gridwidth=1;
        cAdmin.weighty = 1;
        cAdmin.ipady = 0;
        cAdmin.fill = GridBagConstraints.NONE;
        cAdmin.anchor = GridBagConstraints.SOUTH;
        logoutAdmin.setOpaque(true);
        masterPaneAdmin.add(logoutAdmin, cAdmin);

            //Adding Admin Pane to CardLayout
        mainLayout.addLayoutComponent(masterPaneAdmin, "AdminPane");
            //Adding Admin Pane to Parent Panel
        add(masterPaneAdmin);
        
        //Building the Pane for User
        GridBagLayout masterLayoutUser = new GridBagLayout();
        masterPaneUser.setLayout(masterLayoutUser);
        GridBagConstraints cUser = new GridBagConstraints();
        cUser.fill = GridBagConstraints.BOTH;  
        
            //Adding Components to User Pane
        cUser.gridx=0;
        cUser.gridy=0;
        cUser.gridheight=1;
        cUser.gridwidth=3;
        cUser.insets = new Insets(115,60,20,20);
        userLabel.setFont(largeFont);
        userLabel.setOpaque(false);
        userLabel.setForeground(Color.white);
        masterPaneUser.add(userLabel, cUser);
        
        cUser.gridx=1;
        cUser.gridy=1;
        cUser.insets = new Insets(40,80,0,0);
        cUser.gridheight=1;
        cUser.gridwidth=1;
        resetPasswordUser.setOpaque(true);
        masterPaneUser.add(resetPasswordUser, cUser);
        
        cUser.gridx=1;
        cUser.gridy=3;
        cUser.insets = new Insets(0,80,40,0);
        cUser.gridheight=1;
        cUser.gridwidth=1;
        cUser.weighty = 1;
        cUser.ipady = 0;
        cUser.fill = GridBagConstraints.NONE;
        cUser.anchor = GridBagConstraints.SOUTH;
        logoutUser.setOpaque(true);
        masterPaneUser.add(logoutUser, cUser);

            //Adding User Pane to CardLayout
        mainLayout.addLayoutComponent(masterPaneUser, "UserPane");
            //Adding User Pane to Parent Panel
        add(masterPaneUser);
        
        //Building the Pane for Unlock
        GridBagLayout masterLayoutUnlock = new GridBagLayout();
        masterPaneUnlock.setLayout(masterLayoutUnlock);
        GridBagConstraints cUnlock = new GridBagConstraints();
        cUnlock.fill = GridBagConstraints.BOTH;  
        
            //Adding Components to Unlock Pane
        cUnlock.gridx=0;
        cUnlock.gridy=0;
        cUnlock.gridheight=1;
        cUnlock.gridwidth=3;
        cUnlock.insets = new Insets(110,60,20,20);
        unlockLabel.setFont(largeFont);
        unlockLabel.setOpaque(false);
        unlockLabel.setForeground(Color.white);
        masterPaneUnlock.add(unlockLabel, cUnlock);
        
        //cUnlock.fill = GridBagConstraints.NONE; 
        cUnlock.gridx=0;
        cUnlock.gridy=1;
        cUnlock.gridheight=1;
        cUnlock.gridwidth=1;
        cUnlock.insets = new Insets(40,0,10,0);
        cUnlock.ipadx=0;
        accountNameLabelUnlock.setOpaque(true);
        accountNameLabelUnlock.setBackground(Color.black);
        accountNameLabelUnlock.setForeground(Color.white);
        masterPaneUnlock.add(accountNameLabelUnlock, cUnlock);
        
        cUnlock.gridx=1;
        cUnlock.gridy=1;
        cUnlock.gridheight=1;
        cUnlock.gridwidth=2;
        cUnlock.insets = new Insets(40,0,10,0);
        accountNameInputUnlock.setOpaque(true);
        accountNameInputUnlock.setForeground(Color.black);
        accountNameInputUnlock.setCaretColor(Color.black);
        masterPaneUnlock.add(accountNameInputUnlock, cUnlock);
        
        cUnlock.gridx=1;
        cUnlock.gridy=2;
        cUnlock.insets = new Insets(30,65,0,0);
        cUnlock.gridheight=1;
        cUnlock.gridwidth=1;
        cUnlock.fill= GridBagConstraints.NONE;
        unlock.setOpaque(true);
        masterPaneUnlock.add(unlock, cUnlock);
        
        cUnlock.gridx=1;
        cUnlock.gridy=3;
        cUnlock.weighty = 1;
        cUnlock.gridheight=1;
        cUnlock.gridwidth=2;
        cUnlock.fill= GridBagConstraints.BOTH;
        cUnlock.ipady = 150;
        cUnlock.insets = new Insets(0,0,0,0);
        infoAreaUnlock.setOpaque(false);
        //infoAreaUnlock.setBackground(Color.black);
        infoAreaUnlock.setForeground(Color.white);
        infoAreaUnlock.setEditable(false);
        infoAreaUnlock.setLineWrap(true);
        infoAreaUnlock.setWrapStyleWord(true);
        masterPaneUnlock.add(infoAreaUnlock, cUnlock);
        
        cUnlock.gridx=1;
        cUnlock.gridy=4;
        cUnlock.insets = new Insets(0,65,30,0);
        cUnlock.gridheight=1;
        cUnlock.gridwidth=1;
        cUnlock.weighty = 0;
        cUnlock.ipady = 0;
        cUnlock.fill = GridBagConstraints.NONE;
        cUnlock.anchor = GridBagConstraints.SOUTH;
        prevPageUnlock.setOpaque(true);
        masterPaneUnlock.add(prevPageUnlock, cUnlock);

            //Adding Unlock Pane to CardLayout
        mainLayout.addLayoutComponent(masterPaneUnlock, "UnlockPane");
            //Adding Unlock Pane to Parent Panel
        add(masterPaneUnlock);

        //Setting up the Password Reset Pane
        GridBagLayout masterLayoutReset = new GridBagLayout();
        masterPaneReset.setLayout(masterLayoutReset);
        GridBagConstraints cReset = new GridBagConstraints();
        cReset.fill = GridBagConstraints.BOTH; 
        
            //Adding components to Login Pane
        cReset.gridx=0;
        cReset.gridy=0;
        cReset.gridheight=1;
        cReset.gridwidth=3;
        cReset.insets = new Insets(110,85,20,20);
        resetLabel.setFont(largeFont);
        resetLabel.setOpaque(false);
        resetLabel.setForeground(Color.white);
        masterPaneReset.add(resetLabel, cReset);
                
        cReset.gridx=0;
        cReset.gridy=1;
        cReset.gridheight=1;
        cReset.gridwidth=1;
        cReset.insets = new Insets(0,0,10,0);
        cReset.ipadx=0;
        passwordLabelCurr.setOpaque(false);
        passwordLabelCurr.setForeground(Color.white);
        masterPaneReset.add(passwordLabelCurr, cReset);
        
        cReset.gridx=1;
        cReset.gridy=1;
        cReset.gridheight=1;
        cReset.gridwidth=2;
        cReset.insets = new Insets(0,0,10,0);
        passwordInputCurr.setOpaque(true);
        passwordInputCurr.setForeground(Color.black);
        //passwordInputCurr.setText();
        passwordInputCurr.setCaretColor(Color.black);
        masterPaneReset.add(passwordInputCurr, cReset);
                
        cReset.gridx=0;
        cReset.gridy=2;
        cReset.gridheight=1;
        cReset.gridwidth=1;
        cReset.insets = new Insets(0,0,0,0);
        cReset.ipadx = 0;
        passwordLabelNew.setOpaque(false);
        passwordLabelNew.setForeground(Color.white);
        masterPaneReset.add(passwordLabelNew, cReset);
                
        cReset.gridx=1;
        cReset.gridy=2;
        cReset.gridheight=1;
        cReset.gridwidth=2;
        cReset.insets = new Insets(0,0,0,0);
        passwordInputNew.setOpaque(true);
        passwordInputNew.setForeground(Color.black);
        passwordInputNew.setCaretColor(Color.black);
        masterPaneReset.add(passwordInputNew, cReset);
                        
        cReset.gridx=0;
        cReset.gridy=3;
        cReset.gridheight=1;
        cReset.gridwidth=1;
        cReset.insets = new Insets(0,0,0,0);
        cReset.ipadx = 0;
        passwordLabelConfirm.setOpaque(false);
        passwordLabelConfirm.setForeground(Color.white);
        masterPaneReset.add(passwordLabelConfirm, cReset);
                
        cReset.gridx=1;
        cReset.gridy=3;
        cReset.gridheight=1;
        cReset.gridwidth=2;
        cReset.insets = new Insets(0,0,0,0);
        passwordInputConfirm.setOpaque(true);
        passwordInputConfirm.setForeground(Color.black);
        passwordInputConfirm.setCaretColor(Color.black);
        masterPaneReset.add(passwordInputConfirm, cReset);
        
        cReset.gridx=1;
        cReset.gridy=4;
        cReset.insets = new Insets(25,45,0,20);
        cReset.gridheight=1;
        cReset.gridwidth=1;
        cReset.weighty=0;
        cReset.fill= GridBagConstraints.NONE;
        resetPassword.setOpaque(true);
        masterPaneReset.add(resetPassword, cReset);
                
        cReset.gridx=1;
        cReset.gridy=5;
        cReset.weighty = 1;
        cReset.gridheight=1;
        cReset.gridwidth=2;
        cReset.fill= GridBagConstraints.BOTH;
        cReset.ipady = 150;
        cReset.insets = new Insets(10,70,0,0);
        infoAreaReset.setOpaque(false);
        infoAreaReset.setForeground(Color.white);
        infoAreaReset.setEditable(false);
        infoAreaReset.setLineWrap(true);
        infoAreaReset.setWrapStyleWord(true);
        masterPaneReset.add(infoAreaReset, cReset);
                        
        cReset.gridx=0;
        cReset.gridy=7;
        cReset.ipady = 0;
        cReset.insets = new Insets(0,0,30,30);
        cReset.gridheight=1;
        cReset.gridwidth=3;
        cReset.weighty = 0;
        cReset.fill = GridBagConstraints.EAST;
        prevPageReset.setOpaque(true);
        masterPaneLogin.add(prevPageReset, cReset);
       
            //Adding Password Reset Pane to the CardLayout
        mainLayout.addLayoutComponent(masterPaneReset, "ResetPane");
            //Adding Login Pane to the Parent Panel
        add(masterPaneReset);
        
        //Checks to see if the connection to the DB was successful
        this.setResizable(false);
        if (programState == 1) // If it is, display the Login Page
        {   
            displayLogin();
            setVisible(true);
        }
        else if (programState == 2) //If not, display the Server Connection page
        {
            displayServer();
            setVisible(true);
        }
    }
    
    final public void displayLogin()
    {
        paneHistory.push(currPane);
        currPane = "LoginPane";
        mainLayout.show(masterPaneLogin.getParent(), "LoginPane");
    }
    
    final public void displayServer()
    {
        paneHistory.push(currPane);
        currPane  = "ServerPane";
        mainLayout.show(masterPaneServer.getParent(), "ServerPane");
    }
    
    final public void displayCreate()
    {        
        paneHistory.push(currPane);
        currPane = "CreatePane";
        mainLayout.show(masterPaneCreate.getParent(), "CreatePane");
    } 
    
    final public void displayAdmin()
    {        
        paneHistory.push(currPane);
        currPane = "AdminPane";
        mainLayout.show(masterPaneAdmin.getParent(), "AdminPane");
    } 
    
    final public void displayUser()
    {        
        paneHistory.push(currPane);
        currPane = "UserPane";
        mainLayout.show(masterPaneAdmin.getParent(), "UserPane");
    } 
    
    final public void displayUnlock()
    {        
        paneHistory.push(currPane);
        currPane = "UnlockPane";
        mainLayout.show(masterPaneUnlock.getParent(), "UnlockPane");
    } 
    
    final public void displayReset()
    {        
        paneHistory.push(currPane);
        currPane = "ResetPane";
        mainLayout.show(masterPaneUnlock.getParent(), "ResetPane");
    } 
    
    public static void main(String[] args) throws SQLException
    {
        Login_Authenticator loginGui = new Login_Authenticator();
    }
    
}
