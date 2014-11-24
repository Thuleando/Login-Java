/*
 * 
 */
//////////////////////////////////////////////////
//  Imports
//////////////////////////////////////////////////
package login_authenticator;
import java.sql.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;


public class Login_Authenticator extends JFrame 
{
    Login_AuthenticatorEvent loginEvent = new Login_AuthenticatorEvent(this);
 
    //////////////////////////////////////////////////
    //Class Member Variables 
    //////////////////////////////////////////////////
    String query;
    String url;
    int programState=1;
    CardLayout mainLayout;

    //////////////////////////////////////////////////    
    //Properties for creating connection to Oracle database
    //////////////////////////////////////////////////
    Properties props = new Properties();
    Connection conn;
    
    //////////////////////////////////////////////////
    //Components for Login Window
    //////////////////////////////////////////////////
    
        //////////////////////////////////////////////////
        //Labels and Background
        //////////////////////////////////////////////////
    ImageIcon backgroundLogin = new ImageIcon("background5.jpg");
    JLabel masterPaneLogin = new JLabel(backgroundLogin);
    JLabel titleLabel = new JLabel("Account Login");
    JLabel accountNameLabel = new JLabel ("Account:");
    JLabel passwordLabel = new JLabel ("Password:  ");
        //////////////////////////////////////////////////
        //Input fields
        //////////////////////////////////////////////////
    JTextField accountNameInput = new JTextField ();
    JPasswordField passwordInput = new JPasswordField ();
        //////////////////////////////////////////////////
        //Buttons
        //////////////////////////////////////////////////
    JButton login = new JButton("Login");
    JButton exit = new JButton ("Exit");
    JButton createAccount = new JButton("Create Account"); 
        //////////////////////////////////////////////////
        //Display Areas
        //////////////////////////////////////////////////
    JTextArea infoArea = new JTextArea ();
    
    //////////////////////////////////////////////////
    //Components for Server Connection
    //////////////////////////////////////////////////
    
        //////////////////////////////////////////////////
        //Labels and Background
        //////////////////////////////////////////////////
    ImageIcon backgroundServer = new ImageIcon("background5.jpg");
    JLabel masterPaneServer = new JLabel(backgroundServer);
    JLabel serverLabel = new JLabel("Login Server Unavailable");
        //////////////////////////////////////////////////
        //Buttons
        //////////////////////////////////////////////////
    JButton tryAgain  = new JButton ("Try Again");
    JButton serverExit = new JButton ("Exit");
        //////////////////////////////////////////////////
        //Display Areas
        //////////////////////////////////////////////////
    JTextArea serverArea = new JTextArea ();
    
    //////////////////////////////////////////////////
    //Components for Create Account
    //////////////////////////////////////////////////
    
        //////////////////////////////////////////////////
        //Labels and Background
        //////////////////////////////////////////////////
    ImageIcon backgroundCreate = new ImageIcon("background5.jpg");
    JLabel masterPaneCreate = new JLabel(backgroundCreate);
    JLabel createLabel = new JLabel("Account Creation");
    JLabel accountNameLabelCr = new JLabel ("Account:");
    JLabel passwordLabelCr = new JLabel ("Password:  ");
        //////////////////////////////////////////////////
        //Input fields
        //////////////////////////////////////////////////
    JTextField accountNameInputCr = new JTextField ();
    JPasswordField passwordInputCr = new JPasswordField ();
        //////////////////////////////////////////////////
        //Buttons
        //////////////////////////////////////////////////
    JButton create  = new JButton ("Create");
    JButton loginPage = new JButton ("Back to Login");
    JButton createExit = new JButton ("Exit");
        //////////////////////////////////////////////////
        //Display Areas
        //////////////////////////////////////////////////
    JTextArea infoAreaCr = new JTextArea ();
    
    //////////////////////////////////////////////////
    //Components for Admin Pane
    //////////////////////////////////////////////////
    
        //////////////////////////////////////////////////
        //Labels and Background
        //////////////////////////////////////////////////
    ImageIcon backgroundAdmin = new ImageIcon("background3.jpg");
    JLabel masterPaneAdmin = new JLabel(backgroundAdmin);
    JLabel adminLabel = new JLabel("Account Administration");
        //////////////////////////////////////////////////
        //Buttons
        //////////////////////////////////////////////////
    JButton unlockAdmin  = new JButton ("Unlock Account");
    JButton promoteAdmin  = new JButton ("Promote User");
    JButton changePWAdmin = new JButton ("Change Password");
    JButton logoutAdmin = new JButton ("Logout");
    
    //////////////////////////////////////////////////
    //Components for User Pane
    //////////////////////////////////////////////////
    
        //////////////////////////////////////////////////
        //Labels and Background
        //////////////////////////////////////////////////
    ImageIcon backgroundUser = new ImageIcon("background3.jpg");
    JLabel masterPaneUser = new JLabel(backgroundUser);
    JLabel userLabel = new JLabel("Account ****");
        //////////////////////////////////////////////////
        //Buttons
        //////////////////////////////////////////////////
    JButton changePWUser = new JButton ("Change Password");
    JButton logoutUser = new JButton ("Logout");
    
    //////////////////////////////////////////////////
    //Components for Unlock Pane
    //////////////////////////////////////////////////
    
        //////////////////////////////////////////////////
        //Labels and Background
        //////////////////////////////////////////////////
    ImageIcon backgroundUnlock = new ImageIcon("background3.jpg");
    JLabel masterPaneUnlock = new JLabel(backgroundUnlock);
    JLabel unlockLabel = new JLabel("Account Lock Management");
    JLabel accountNameLabelUnlock = new JLabel ("Account:  ");
    JLabel reasonLabelUnlock = new JLabel ("Unlock Reason:  ");
        //////////////////////////////////////////////////
        //Input fields
        //////////////////////////////////////////////////
    JComboBox<String> userListUnlock = new JComboBox<String>(new Vector<String>());
    JComboBox<String> reasonListUnlock = new JComboBox<String>(new Vector<String>());
        //////////////////////////////////////////////////
        //Buttons
        //////////////////////////////////////////////////
    JButton unlock  = new JButton ("Unlock");
    JButton backUnlock = new JButton ("Back");
        //////////////////////////////////////////////////
        //Display Areas
        //////////////////////////////////////////////////
    JTextArea infoAreaUnlock = new JTextArea ();

    //////////////////////////////////////////////////
    //Components for Promote Pane
    //////////////////////////////////////////////////
    
        //////////////////////////////////////////////////
        //Labels and Background
        //////////////////////////////////////////////////
    ImageIcon backgroundPromote = new ImageIcon("background3.jpg");
    JLabel masterPanePromote = new JLabel(backgroundPromote);
    JLabel promoteLabel = new JLabel("Promote User to Admin");
    JLabel accountNameLabelPromote = new JLabel ("Account:  ");
        //////////////////////////////////////////////////
        //Input fields
        //////////////////////////////////////////////////
    JComboBox<String> userListPromote = new JComboBox<String>(new Vector<String>());
        //////////////////////////////////////////////////
        //Buttons
        //////////////////////////////////////////////////
    JButton promote  = new JButton ("Promote");
    JButton backPromote = new JButton ("Back");
        //////////////////////////////////////////////////
        //Display Areas
        //////////////////////////////////////////////////
    JTextArea infoAreaPromote = new JTextArea ();
    
    //////////////////////////////////////////////////
    //Components for Change Password Window for User
    //////////////////////////////////////////////////
    
        //////////////////////////////////////////////////
        //Labels and Background
        //////////////////////////////////////////////////
    ImageIcon backgroundChangePW = new ImageIcon("background3.jpg");
    JLabel masterPaneChangePWU = new JLabel(backgroundChangePW);
    JLabel changePWLabel = new JLabel("Change Password");
    JLabel passwordLabelCurr = new JLabel ("Current Password: ");
    JLabel passwordLabelNew = new JLabel ("New Password: ");
    JLabel passwordLabelConfirm = new JLabel ("Confirm Password: ");
        //////////////////////////////////////////////////
        //Input fields
        //////////////////////////////////////////////////
    JPasswordField passwordInputCurr = new JPasswordField ();
    JPasswordField passwordInputNew = new JPasswordField ();
    JPasswordField passwordInputConfirm = new JPasswordField ();
        //////////////////////////////////////////////////
        //Buttons
        //////////////////////////////////////////////////
    JButton changePW = new JButton("Change");
    JButton backChangePW = new JButton ("Back");    
        //////////////////////////////////////////////////
        //Display Areas
        //////////////////////////////////////////////////
    JTextArea infoAreaChangePW = new JTextArea ();
    
    //////////////////////////////////////////////////
    //Components for Change Password Window for Admin
    //////////////////////////////////////////////////
    
        //////////////////////////////////////////////////
        //Labels and Background
        //////////////////////////////////////////////////
    ImageIcon backgroundChangePWAdmin = new ImageIcon("background3.jpg");
    JLabel masterPaneChangePWA = new JLabel(backgroundChangePWAdmin);
    JLabel changePWALabel = new JLabel("Change Password");
    JLabel accountNameLabelA = new JLabel ("Account: ");
    JLabel passwordLabelANew = new JLabel ("New Password: ");
    JLabel passwordLabelAConfirm = new JLabel ("Confirm Password: ");
        //////////////////////////////////////////////////
        //Input fields
        //////////////////////////////////////////////////
    JTextField accountNameInputA = new JTextField ();
    JPasswordField passwordInputANew = new JPasswordField ();
    JPasswordField passwordInputAConfirm = new JPasswordField ();
        //////////////////////////////////////////////////
        //Buttons
        //////////////////////////////////////////////////
    JButton changePWA = new JButton("Change");
    JButton backChangePWA = new JButton ("Back");    
        //////////////////////////////////////////////////
        //Display Areas
        //////////////////////////////////////////////////
    JTextArea infoAreaChangePWA = new JTextArea ();
    
    //////////////////////////////////////////////////
    //Constructor
    //////////////////////////////////////////////////
    public Login_Authenticator()  throws SQLException
    { 
        
        super();
        
        //////////////////////////////////////////////////
        //Setup for Connection with the Database
        //////////////////////////////////////////////////
        url = "jdbc:oracle:thin:@cncsidb01.msudenver.edu:1521:DB01";
        props.setProperty("user", "SKYWALKER");
        props.setProperty("password", "LUKE");        
     
        try
        {
            //creating connection to Oracle database using JDBC
            conn = DriverManager.getConnection(url,props);
            //Statement stmt = conn.createStatement();
            //stmt = conn.createStatement();
        }
        catch(SQLException a)
        {
            System.out.println("Exception being thrown\n" + a );
            programState = 2;
        }
       
        //////////////////////////////////////////////////
        //Setup the Parent Panel
        //////////////////////////////////////////////////
        
        this.setLocationRelativeTo(null);
        setUndecorated(true);        
        setSize(490,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainLayout = new CardLayout();
        setLayout(mainLayout);
        
        //Create a font to use in Headers
        Font largeFont = new Font("Dialog", Font.BOLD, 18);
        
        //////////////////////////////////////////////////
        //Add Listeners
        //////////////////////////////////////////////////
    
            //////////////////////////////////////////////////
            // Main Panel Listener
            //////////////////////////////////////////////////
        addMouseListener(loginEvent);
        addMouseMotionListener(loginEvent);
        
            //////////////////////////////////////////////////
            //Input Pane Listeners
            //////////////////////////////////////////////////
        accountNameInput.addKeyListener(loginEvent);
        accountNameInput.addFocusListener(loginEvent);
        passwordInput.addKeyListener(loginEvent);
        passwordInput.addFocusListener(loginEvent);
        login.addActionListener(loginEvent);
        exit.addActionListener(loginEvent);
        createAccount.addActionListener(loginEvent);

            //////////////////////////////////////////////////
            //Server Pane Listeners
            //////////////////////////////////////////////////
        tryAgain.addActionListener(loginEvent);
        serverExit.addActionListener(loginEvent);
        
            //////////////////////////////////////////////////
            //Create Pane Listeners
            //////////////////////////////////////////////////
        accountNameInputCr.addKeyListener(loginEvent);
        accountNameInputCr.addFocusListener(loginEvent);
        passwordInputCr.addKeyListener(loginEvent);
        passwordInputCr.addFocusListener(loginEvent); 
        loginPage.addActionListener(loginEvent);
        createExit.addActionListener(loginEvent);
        create.addActionListener(loginEvent);
        
            //////////////////////////////////////////////////
            //Admin Pane Listeners
            //////////////////////////////////////////////////
        unlockAdmin.addActionListener(loginEvent);
        promoteAdmin.addActionListener(loginEvent);
        changePWAdmin.addActionListener(loginEvent);
        logoutAdmin.addActionListener(loginEvent);
        
            //////////////////////////////////////////////////
            //User Pane Listeners
            //////////////////////////////////////////////////
        changePWUser.addActionListener(loginEvent);
        logoutUser.addActionListener(loginEvent);
    
            //////////////////////////////////////////////////
            //Unlock Pane Listeners
            //////////////////////////////////////////////////
        unlock.addActionListener(loginEvent);
        backUnlock.addActionListener(loginEvent);
        userListUnlock.addKeyListener(loginEvent);
        reasonListUnlock.addKeyListener(loginEvent);
        
            //////////////////////////////////////////////////
            //Promote Pane Listeners
            //////////////////////////////////////////////////
        promote.addActionListener(loginEvent);
        backPromote.addActionListener(loginEvent);
        userListPromote.addKeyListener(loginEvent);
        
            //////////////////////////////////////////////////
            //ChangePWU Pane Listeners
            //////////////////////////////////////////////////
        passwordInputCurr.addKeyListener(loginEvent);
        passwordInputCurr.addFocusListener(loginEvent);
        passwordInputNew.addKeyListener(loginEvent);
        passwordInputNew.addFocusListener(loginEvent);
        passwordInputConfirm.addKeyListener(loginEvent);
        passwordInputConfirm.addFocusListener(loginEvent);
        changePW.addActionListener(loginEvent);
        backChangePW.addActionListener(loginEvent);
        
            //////////////////////////////////////////////////
            //ChangePWA Pane Listeners
            //////////////////////////////////////////////////
        accountNameInputA.addKeyListener(loginEvent);
        accountNameInputA.addFocusListener(loginEvent);
        passwordInputANew.addKeyListener(loginEvent);
        passwordInputANew.addFocusListener(loginEvent);
        passwordInputAConfirm.addKeyListener(loginEvent);
        passwordInputAConfirm.addFocusListener(loginEvent);
        changePWA.addActionListener(loginEvent);
        backChangePWA.addActionListener(loginEvent);
        
        //////////////////////////////////////////////////
        //Setting up the Login Pane
        //////////////////////////////////////////////////
        GridBagLayout masterLayoutLogin = new GridBagLayout();
        masterPaneLogin.setLayout(masterLayoutLogin);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; 
        c.weighty = 0;
        
            //////////////////////////////////////////////////
            //Adding components to Login Pane
            //////////////////////////////////////////////////
        c.gridx=0;
        c.gridy=0;
        c.gridheight=1;
        c.gridwidth=3;
        c.insets = new Insets(130,85,20,20);
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
        c.insets = new Insets(0,0,30,0);
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
        
        //////////////////////////////////////////////////
        //Building the Pane for Server
        //////////////////////////////////////////////////
        GridBagLayout masterLayoutServer = new GridBagLayout();
        masterPaneServer.setLayout(masterLayoutServer);
        GridBagConstraints cServ = new GridBagConstraints();
        cServ.fill = GridBagConstraints.BOTH;  
            //////////////////////////////////////////////////
            //Adding Components to Server Pane
            //////////////////////////////////////////////////
        cServ.gridx=0;
        cServ.gridy=0;
        cServ.gridheight=1;
        cServ.gridwidth=3;
        cServ.insets = new Insets(130,60,20,20);
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
        
        //////////////////////////////////////////////////
        //Building the Pane for Create
        //////////////////////////////////////////////////
        GridBagLayout masterLayoutCreate = new GridBagLayout();
        masterPaneCreate.setLayout(masterLayoutCreate);
        GridBagConstraints cCr = new GridBagConstraints();
        cCr.fill = GridBagConstraints.BOTH; 
               
            //////////////////////////////////////////////////
            //Adding Components to Create Pane
            //////////////////////////////////////////////////
        cCr.gridx=0;
        cCr.gridy=0;
        cCr.weighty = 0;
        cCr.gridheight=1;
        cCr.gridwidth=3;
        cCr.insets = new Insets(130,85,20,20);
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
        cCr.insets = new Insets(0,0,30,0);
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
        
        //////////////////////////////////////////////////
        //Building the Pane for Admin
        //////////////////////////////////////////////////
        GridBagLayout masterLayoutAdmin = new GridBagLayout();
        masterPaneAdmin.setLayout(masterLayoutAdmin);
        GridBagConstraints cAdmin = new GridBagConstraints();
        cAdmin.fill = GridBagConstraints.BOTH;
        cAdmin.anchor = GridBagConstraints.CENTER;
        
            //////////////////////////////////////////////////
            //Adding Components to Admin Pane
            //////////////////////////////////////////////////
        cAdmin.gridx=0;
        cAdmin.gridy=0;
        cAdmin.gridheight=1;
        cAdmin.gridwidth=1;
        cAdmin.insets = new Insets(120,0,0,0);
        adminLabel.setFont(largeFont);
        adminLabel.setOpaque(false);
        adminLabel.setForeground(Color.white);
        masterPaneAdmin.add(adminLabel, cAdmin);
        
        cAdmin.gridx=0;
        cAdmin.gridy=1;
        cAdmin.insets = new Insets(70,0,0,0);
        cAdmin.gridheight=1;
        cAdmin.gridwidth=1;
        cAdmin.weighty=0;
        cAdmin.fill= GridBagConstraints.NONE;
        unlockAdmin.setOpaque(true);
        masterPaneAdmin.add(unlockAdmin, cAdmin);
        
        cAdmin.gridx=0;
        cAdmin.gridy=2;
        cAdmin.insets = new Insets(20,0,0,0);
        cAdmin.gridheight=1;
        cAdmin.gridwidth=1;
        cAdmin.weighty=0;
        cAdmin.fill= GridBagConstraints.NONE;
        promoteAdmin.setOpaque(true);
        masterPaneAdmin.add(promoteAdmin, cAdmin);
        
        cAdmin.gridx=0;
        cAdmin.gridy=3;
        cAdmin.insets = new Insets(20,0,0,0);
        cAdmin.gridheight=1;
        cAdmin.gridwidth=3;
        changePWAdmin.setOpaque(true);
        masterPaneAdmin.add(changePWAdmin, cAdmin);
        
        cAdmin.gridx=0;
        cAdmin.gridy=4;
        cAdmin.insets = new Insets(0,0,30,0);
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
        
        //////////////////////////////////////////////////
        //Building the Pane for User
        //////////////////////////////////////////////////
        GridBagLayout masterLayoutUser = new GridBagLayout();
        masterPaneUser.setLayout(masterLayoutUser);
        GridBagConstraints cUser = new GridBagConstraints();
        cUser.fill = GridBagConstraints.BOTH;  
            
            //////////////////////////////////////////////////
            //Adding Components to User Pane
            //////////////////////////////////////////////////
        cUser.gridx=0;
        cUser.gridy=0;
        cUser.gridheight=1;
        cUser.gridwidth=1;
        cUser.insets = new Insets(125,0,0,0);
        cUser.anchor = GridBagConstraints.CENTER;
        userLabel.setFont(largeFont);
        userLabel.setOpaque(false);
        userLabel.setForeground(Color.white);
        masterPaneUser.add(userLabel, cUser);
        
        cUser.gridx=0;
        cUser.gridy=1;
        cUser.insets = new Insets(100,0,0,0);
        cUser.gridheight=1;
        cUser.gridwidth=1;
        changePWUser.setOpaque(true);
        masterPaneUser.add(changePWUser, cUser);
        
        cUser.gridx=0;
        cUser.gridy=2;
        cUser.insets = new Insets(0,0,30,0);
        cUser.gridheight=1;
        cUser.gridwidth=1;
        cUser.weighty = 1;
        cUser.fill = GridBagConstraints.NONE;
        cUser.anchor = GridBagConstraints.SOUTH;
        logoutUser.setOpaque(true);
        masterPaneUser.add(logoutUser, cUser);

            //Adding User Pane to CardLayout
        mainLayout.addLayoutComponent(masterPaneUser, "UserPane");
            //Adding User Pane to Parent Panel
        add(masterPaneUser);
        
        //////////////////////////////////////////////////
        //Building the Pane for Unlock
        //////////////////////////////////////////////////
        GridBagLayout masterLayoutUnlock = new GridBagLayout();
        masterPaneUnlock.setLayout(masterLayoutUnlock);
        GridBagConstraints cUnlock = new GridBagConstraints();
        cUnlock.fill = GridBagConstraints.BOTH; 
        cUnlock.anchor = GridBagConstraints.CENTER;
        
            //////////////////////////////////////////////////
            //Adding Components to Unlock Pane
            //////////////////////////////////////////////////
        cUnlock.gridx=0;
        cUnlock.gridy=0;
        cUnlock.gridheight=1;
        cUnlock.gridwidth=2;
        cUnlock.insets = new Insets(120,0,0,00);
        unlockLabel.setFont(largeFont);
        unlockLabel.setOpaque(false);
        unlockLabel.setForeground(Color.white);
        masterPaneUnlock.add(unlockLabel, cUnlock);
        
        //cUnlock.fill = GridBagConstraints.NONE; 
        cUnlock.gridx=0;
        cUnlock.gridy=1;
        cUnlock.gridheight=1;
        cUnlock.gridwidth=1;
        cUnlock.insets = new Insets(100,0,0,0);
        cUnlock.ipadx=0;
        cUnlock.fill = GridBagConstraints.HORIZONTAL;  
        accountNameLabelUnlock.setOpaque(false);
        accountNameLabelUnlock.setBackground(Color.black);
        accountNameLabelUnlock.setForeground(Color.white);
        masterPaneUnlock.add(accountNameLabelUnlock, cUnlock);
        
        cUnlock.gridx=1;
        cUnlock.gridy=1;
        cUnlock.gridheight=1;
        cUnlock.gridwidth=2;
        cUnlock.insets = new Insets(100,0,0,0);
        userListUnlock.setOpaque(true);
        userListUnlock.setForeground(Color.black);
        masterPaneUnlock.add(userListUnlock, cUnlock);
        
        cUnlock.gridx=0;
        cUnlock.gridy=2;
        cUnlock.gridheight=1;
        cUnlock.gridwidth=1;
        cUnlock.insets = new Insets(0,0,0,0);
        cUnlock.ipadx=0;
        cUnlock.fill = GridBagConstraints.HORIZONTAL;  
        reasonLabelUnlock.setOpaque(false);
        reasonLabelUnlock.setBackground(Color.black);
        reasonLabelUnlock.setForeground(Color.white);
        masterPaneUnlock.add(reasonLabelUnlock, cUnlock);
        
        cUnlock.gridx=1;
        cUnlock.gridy=2;
        cUnlock.gridheight=1;
        cUnlock.gridwidth=2;
        cUnlock.insets = new Insets(0,0,0,0);
        reasonListUnlock.setOpaque(true);
        reasonListUnlock.setForeground(Color.black);
        masterPaneUnlock.add(reasonListUnlock, cUnlock);
        
        cUnlock.gridx=1;
        cUnlock.gridy=3;
        cUnlock.insets = new Insets(30,0,0,0);
        cUnlock.gridheight=1;
        cUnlock.gridwidth=1;
        cUnlock.fill= GridBagConstraints.NONE;
        unlock.setOpaque(true);
        masterPaneUnlock.add(unlock, cUnlock);
        
        cUnlock.gridx=1;
        cUnlock.gridy=4;
        cUnlock.weighty = 1;
        cUnlock.gridheight=1;
        cUnlock.gridwidth=2;
        cUnlock.fill= GridBagConstraints.BOTH;
        cUnlock.ipady = 150;
        cUnlock.insets = new Insets(10,0,0,0);
        infoAreaUnlock.setOpaque(false);
        //infoAreaUnlock.setBackground(Color.black);
        infoAreaUnlock.setForeground(Color.white);
        infoAreaUnlock.setEditable(false);
        infoAreaUnlock.setLineWrap(true);
        infoAreaUnlock.setWrapStyleWord(true);
        masterPaneUnlock.add(infoAreaUnlock, cUnlock);
        
        cUnlock.gridx=0;
        cUnlock.gridy=5;
        cUnlock.insets = new Insets(0,0,30,0);
        cUnlock.gridheight=1;
        cUnlock.gridwidth=2;
        cUnlock.weighty = 0;
        cUnlock.ipady = 0;
        cUnlock.fill = GridBagConstraints.NONE;
        cUnlock.anchor = GridBagConstraints.SOUTH;
        backUnlock.setOpaque(true);
        masterPaneUnlock.add(backUnlock, cUnlock);

            //Adding Unlock Pane to CardLayout
        mainLayout.addLayoutComponent(masterPaneUnlock, "UnlockPane");
            //Adding Unlock Pane to Parent Panel
        add(masterPaneUnlock);
        
        //////////////////////////////////////////////////
        //Building the Pane for Promote
        //////////////////////////////////////////////////
        GridBagLayout masterLayoutPromote = new GridBagLayout();
        masterPanePromote.setLayout(masterLayoutPromote);
        GridBagConstraints cPromote = new GridBagConstraints();
        cPromote.fill = GridBagConstraints.BOTH; 
        cPromote.anchor = GridBagConstraints.CENTER;
        
            //////////////////////////////////////////////////
            //Adding Components to Promote Pane
            //////////////////////////////////////////////////
        cPromote.gridx=0;
        cPromote.gridy=0;
        cPromote.gridheight=1;
        cPromote.gridwidth=2;
        cPromote.insets = new Insets(120,0,0,00);
        promoteLabel.setFont(largeFont);
        promoteLabel.setOpaque(false);
        promoteLabel.setForeground(Color.white);
        masterPanePromote.add(promoteLabel, cPromote);
        
        //cPromote.fill = GridBagConstraints.NONE; 
        cPromote.gridx=0;
        cPromote.gridy=1;
        cPromote.gridheight=1;
        cPromote.gridwidth=1;
        cPromote.insets = new Insets(100,0,0,0);
        cPromote.ipadx=0;
        cPromote.fill = GridBagConstraints.HORIZONTAL;  
        accountNameLabelPromote.setOpaque(false);
        accountNameLabelPromote.setBackground(Color.black);
        accountNameLabelPromote.setForeground(Color.white);
        masterPanePromote.add(accountNameLabelPromote, cPromote);
        
        cPromote.gridx=1;
        cPromote.gridy=1;
        cPromote.gridheight=1;
        cPromote.gridwidth=2;
        cPromote.insets = new Insets(100,0,0,0);
        userListPromote.setOpaque(true);
        userListPromote.setForeground(Color.black);
        masterPanePromote.add(userListPromote, cPromote);
        
        cPromote.gridx=1;
        cPromote.gridy=2;
        cPromote.insets = new Insets(30,0,0,0);
        cPromote.gridheight=1;
        cPromote.gridwidth=1;
        cPromote.fill= GridBagConstraints.NONE;
        promote.setOpaque(true);
        masterPanePromote.add(promote, cPromote);
        
        cPromote.gridx=1;
        cPromote.gridy=3;
        cPromote.weighty = 1;
        cPromote.gridheight=1;
        cPromote.gridwidth=2;
        cPromote.fill= GridBagConstraints.BOTH;
        cPromote.ipady = 150;
        cPromote.insets = new Insets(10,0,0,0);
        infoAreaPromote.setOpaque(false);
        //infoAreaPromote.setBackground(Color.black);
        infoAreaPromote.setForeground(Color.white);
        infoAreaPromote.setEditable(false);
        infoAreaPromote.setLineWrap(true);
        infoAreaPromote.setWrapStyleWord(true);
        masterPanePromote.add(infoAreaPromote, cPromote);
        
        cPromote.gridx=0;
        cPromote.gridy=4;
        cPromote.insets = new Insets(0,0,30,0);
        cPromote.gridheight=1;
        cPromote.gridwidth=2;
        cPromote.weighty = 0;
        cPromote.ipady = 0;
        cPromote.fill = GridBagConstraints.NONE;
        cPromote.anchor = GridBagConstraints.SOUTH;
        backPromote.setOpaque(true);
        masterPanePromote.add(backPromote, cPromote);

            //Adding Promote Pane to CardLayout
        mainLayout.addLayoutComponent(masterPanePromote, "PromotePane");
            //Adding Promote Pane to Parent Panel
        add(masterPanePromote);

        //////////////////////////////////////////////////
        //Setting up the Change Password Pane for User
        //////////////////////////////////////////////////
        GridBagLayout masterLayoutChangePW = new GridBagLayout();
        masterPaneChangePWU.setLayout(masterLayoutChangePW);
        GridBagConstraints cChangePW = new GridBagConstraints();
        cChangePW.fill = GridBagConstraints.BOTH; 
        
            //////////////////////////////////////////////////
            //Adding components to ChangePWU Pane
            //////////////////////////////////////////////////
        cChangePW.gridx=0;
        cChangePW.gridy=0;
        cChangePW.gridheight=1;
        cChangePW.gridwidth=3;
        cChangePW.insets = new Insets(120,55,20,20);
        changePWLabel.setFont(largeFont);
        changePWLabel.setOpaque(false);
        changePWLabel.setForeground(Color.white);
        masterPaneChangePWU.add(changePWLabel, cChangePW);
                
        cChangePW.gridx=0;
        cChangePW.gridy=1;
        cChangePW.gridheight=1;
        cChangePW.gridwidth=1;
        cChangePW.insets = new Insets(50,0,0,0);
        cChangePW.ipadx=0;
        passwordLabelCurr.setOpaque(false);
        passwordLabelCurr.setForeground(Color.white);
        masterPaneChangePWU.add(passwordLabelCurr, cChangePW);
        
        cChangePW.gridx=1;
        cChangePW.gridy=1;
        cChangePW.gridheight=1;
        cChangePW.gridwidth=2;
        cChangePW.insets = new Insets(50,0,0,0);
        passwordInputCurr.setOpaque(true);
        passwordInputCurr.setForeground(Color.black);
        //passwordInputCurr.setText();
        passwordInputCurr.setCaretColor(Color.black);
        masterPaneChangePWU.add(passwordInputCurr, cChangePW);
                
        cChangePW.gridx=0;
        cChangePW.gridy=2;
        cChangePW.gridheight=1;
        cChangePW.gridwidth=1;
        cChangePW.insets = new Insets(20,0,0,0);
        cChangePW.ipadx = 0;
        passwordLabelNew.setOpaque(false);
        passwordLabelNew.setForeground(Color.white);
        masterPaneChangePWU.add(passwordLabelNew, cChangePW);
                
        cChangePW.gridx=1;
        cChangePW.gridy=2;
        cChangePW.gridheight=1;
        cChangePW.gridwidth=2;
        cChangePW.insets = new Insets(20,0,0,0);
        passwordInputNew.setOpaque(true);
        passwordInputNew.setForeground(Color.black);
        passwordInputNew.setCaretColor(Color.black);
        masterPaneChangePWU.add(passwordInputNew, cChangePW);
                        
        cChangePW.gridx=0;
        cChangePW.gridy=3;
        cChangePW.gridheight=1;
        cChangePW.gridwidth=1;
        cChangePW.insets = new Insets(10,0,0,0);
        cChangePW.ipadx = 0;
        passwordLabelConfirm.setOpaque(false);
        passwordLabelConfirm.setForeground(Color.white);
        masterPaneChangePWU.add(passwordLabelConfirm, cChangePW);
                
        cChangePW.gridx=1;
        cChangePW.gridy=3;
        cChangePW.gridheight=1;
        cChangePW.gridwidth=2;
        cChangePW.insets = new Insets(10,0,0,0);
        passwordInputConfirm.setOpaque(true);
        passwordInputConfirm.setForeground(Color.black);
        passwordInputConfirm.setCaretColor(Color.black);
        masterPaneChangePWU.add(passwordInputConfirm, cChangePW);
        
        cChangePW.gridx=1;
        cChangePW.gridy=4;
        cChangePW.insets = new Insets(25,45,0,20);
        cChangePW.gridheight=1;
        cChangePW.gridwidth=1;
        cChangePW.weighty=0;
        cChangePW.fill= GridBagConstraints.NONE;
        changePW.setOpaque(true);
        masterPaneChangePWU.add(changePW, cChangePW);
                
        cChangePW.gridx=1;
        cChangePW.gridy=5;
        cChangePW.weighty = 1;
        cChangePW.gridheight=1;
        cChangePW.gridwidth=2;
        cChangePW.fill= GridBagConstraints.BOTH;
        cChangePW.ipady = 100;
        cChangePW.insets = new Insets(10,0,0,0);
        infoAreaChangePW.setOpaque(false);
        infoAreaChangePW.setForeground(Color.white);
        infoAreaChangePW.setEditable(false);
        infoAreaChangePW.setLineWrap(true);
        infoAreaChangePW.setWrapStyleWord(true);
        masterPaneChangePWU.add(infoAreaChangePW, cChangePW);
                        
        cChangePW.gridx=0;
        cChangePW.gridy=6;
        cChangePW.ipady = 0;
        cChangePW.insets = new Insets(0,0,30,0);
        cChangePW.gridheight=1;
        cChangePW.gridwidth=3;
        cChangePW.weighty = 0;
        cChangePW.fill= GridBagConstraints.NONE;
        cChangePW.anchor = GridBagConstraints.CENTER;
        backChangePW.setOpaque(true);
        masterPaneChangePWU.add(backChangePW, cChangePW);
       
            //Adding ChangePWU Pane to the CardLayout
        mainLayout.addLayoutComponent(masterPaneChangePWU, "ChangePWUPane");
            //Adding ChangePWU Pane to the Parent Panel
        add(masterPaneChangePWU);
        
        //////////////////////////////////////////////////
        //Setting up the Change Password Pane for Admin
        //////////////////////////////////////////////////
        GridBagLayout masterLayoutChangePWAdmin = new GridBagLayout();
        masterPaneChangePWA.setLayout(masterLayoutChangePWAdmin);
        GridBagConstraints cChangePWA = new GridBagConstraints();
        cChangePWA.fill = GridBagConstraints.BOTH; 
        
            //////////////////////////////////////////////////
            //Adding components to ChangePWA Pane
            //////////////////////////////////////////////////
        cChangePWA.gridx=0;
        cChangePWA.gridy=0;
        cChangePWA.gridheight=1;
        cChangePWA.gridwidth=3;
        cChangePWA.insets = new Insets(120,55,20,20);
        changePWALabel.setFont(largeFont);
        changePWALabel.setOpaque(false);
        changePWALabel.setForeground(Color.white);
        masterPaneChangePWA.add(changePWALabel, cChangePWA);
                
        cChangePWA.gridx=0;
        cChangePWA.gridy=1;
        cChangePWA.gridheight=1;
        cChangePWA.gridwidth=1;
        cChangePWA.insets = new Insets(50,0,0,0);
        cChangePWA.ipadx=0;
        accountNameLabelA.setOpaque(false);
        accountNameLabelA.setForeground(Color.white);
        masterPaneChangePWA.add(accountNameLabelA, cChangePWA);
        
        cChangePWA.gridx=1;
        cChangePWA.gridy=1;
        cChangePWA.gridheight=1;
        cChangePWA.gridwidth=2;
        cChangePWA.insets = new Insets(50,0,0,0);
        accountNameInputA.setOpaque(true);
        accountNameInputA.setForeground(Color.black);
        //passwordInputCurr.setText();
        accountNameInputA.setCaretColor(Color.black);
        masterPaneChangePWA.add(accountNameInputA, cChangePWA);
                
        cChangePWA.gridx=0;
        cChangePWA.gridy=2;
        cChangePWA.gridheight=1;
        cChangePWA.gridwidth=1;
        cChangePWA.insets = new Insets(20,0,0,0);
        cChangePWA.ipadx = 0;
        passwordLabelANew.setOpaque(false);
        passwordLabelANew.setForeground(Color.white);
        masterPaneChangePWA.add(passwordLabelANew, cChangePWA);
                
        cChangePWA.gridx=1;
        cChangePWA.gridy=2;
        cChangePWA.gridheight=1;
        cChangePWA.gridwidth=2;
        cChangePWA.insets = new Insets(20,0,0,0);
        passwordInputANew.setOpaque(true);
        passwordInputANew.setForeground(Color.black);
        passwordInputANew.setCaretColor(Color.black);
        masterPaneChangePWA.add(passwordInputANew, cChangePWA);
                        
        cChangePWA.gridx=0;
        cChangePWA.gridy=3;
        cChangePWA.gridheight=1;
        cChangePWA.gridwidth=1;
        cChangePWA.insets = new Insets(10,0,0,0);
        cChangePWA.ipadx = 0;
        passwordLabelAConfirm.setOpaque(false);
        passwordLabelAConfirm.setForeground(Color.white);
        masterPaneChangePWA.add(passwordLabelAConfirm, cChangePWA);
                
        cChangePWA.gridx=1;
        cChangePWA.gridy=3;
        cChangePWA.gridheight=1;
        cChangePWA.gridwidth=2;
        cChangePWA.insets = new Insets(10,0,0,0);
        passwordInputAConfirm.setOpaque(true);
        passwordInputAConfirm.setForeground(Color.black);
        passwordInputAConfirm.setCaretColor(Color.black);
        masterPaneChangePWA.add(passwordInputAConfirm, cChangePWA);
        
        cChangePWA.gridx=1;
        cChangePWA.gridy=4;
        cChangePWA.insets = new Insets(25,45,0,20);
        cChangePWA.gridheight=1;
        cChangePWA.gridwidth=1;
        cChangePWA.weighty=0;
        cChangePWA.fill= GridBagConstraints.NONE;
        changePWA.setOpaque(true);
        masterPaneChangePWA.add(changePWA, cChangePWA);
                
        cChangePWA.gridx=1;
        cChangePWA.gridy=5;
        cChangePWA.weighty = 1;
        cChangePWA.gridheight=1;
        cChangePWA.gridwidth=2;
        cChangePWA.fill= GridBagConstraints.BOTH;
        cChangePWA.ipady = 100;
        cChangePWA.insets = new Insets(10,0,0,0);
        infoAreaChangePWA.setOpaque(false);
        infoAreaChangePWA.setForeground(Color.white);
        infoAreaChangePWA.setEditable(false);
        infoAreaChangePWA.setLineWrap(true);
        infoAreaChangePWA.setWrapStyleWord(true);
        masterPaneChangePWA.add(infoAreaChangePWA, cChangePWA);
                        
        cChangePWA.gridx=0;
        cChangePWA.gridy=6;
        cChangePWA.ipady = 0;
        cChangePWA.insets = new Insets(0,0,30,0);
        cChangePWA.gridheight=1;
        cChangePWA.gridwidth=3;
        cChangePWA.weighty = 0;
        cChangePWA.fill= GridBagConstraints.NONE;
        cChangePWA.anchor = GridBagConstraints.CENTER;
        backChangePWA.setOpaque(true);
        masterPaneChangePWA.add(backChangePWA, cChangePWA);
       
            //Adding ChangePWA Pane to the CardLayout
        mainLayout.addLayoutComponent(masterPaneChangePWA, "ChangePWAPane");
            //Adding ChangePWA Pane to the Parent Panel
        add(masterPaneChangePWA);
        
        //////////////////////////////////////////////////
        //Checks to see if the connection to the DB was successful
        //////////////////////////////////////////////////
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
    
    //////////////////////////////////////////////////
    //Methods to display Panes
    //////////////////////////////////////////////////
    
    final public void displayLogin()
    {
        loginEvent.paneHistory.push(loginEvent.currPane);
        loginEvent.currPane = "LoginPane";
        mainLayout.show(masterPaneLogin.getParent(), "LoginPane");
        accountNameInput.requestFocusInWindow();
    }
    
    final public void displayServer()
    {
        mainLayout.show(masterPaneServer.getParent(), "ServerPane");
    }
    
    final public void displayCreate()
    {        
        loginEvent.paneHistory.push(loginEvent.currPane);
        loginEvent.currPane = "CreatePane";
        mainLayout.show(masterPaneCreate.getParent(), "CreatePane");
        accountNameInputCr.requestFocus();
    } 
    
    final public void displayAdmin()
    {      
        loginEvent.paneHistory.push(loginEvent.currPane);
        loginEvent.currPane = "AdminPane";
        mainLayout.show(masterPaneAdmin.getParent(), "AdminPane");
    } 
    
    final public void displayUser()
    {        
        loginEvent.paneHistory.push(loginEvent.currPane);
        loginEvent.currPane = "UserPane";
        mainLayout.show(masterPaneAdmin.getParent(), "UserPane");
    } 
    
    final public void displayUnlock()
    {        
        loginEvent.paneHistory.push(loginEvent.currPane);
        loginEvent.currPane = "UnlockPane";
        mainLayout.show(masterPaneUnlock.getParent(), "UnlockPane");
        userListUnlock.requestFocusInWindow();
        infoAreaUnlock.setText(null);
    } 
    
    final public void displayPromote()
    {        
        loginEvent.paneHistory.push(loginEvent.currPane);
        loginEvent.currPane = "PromotePane";
        mainLayout.show(masterPanePromote.getParent(), "PromotePane");
        userListPromote.requestFocusInWindow();
        infoAreaPromote.setText(null);
    } 
    
    final public void displayChangePWU()
    {        
        loginEvent.paneHistory.push(loginEvent.currPane);
        loginEvent.currPane = "ChangePWUPane";
        mainLayout.show(masterPaneChangePWU.getParent(), "ChangePWUPane");
        passwordInputCurr.requestFocusInWindow();
    } 
    
    final public void displayChangePWA()
    {  
        loginEvent.paneHistory.push(loginEvent.currPane);
        loginEvent.currPane = "ChangePWAPane";
        mainLayout.show(masterPaneChangePWA.getParent(), "ChangePWAPane");
        accountNameInputA.requestFocusInWindow();
    }
    
    //////////////////////////////////////////////////
    // Main Method
    //////////////////////////////////////////////////
    public static void main(String[] args) throws SQLException
    {
        Login_Authenticator loginGui = new Login_Authenticator();
    }
    
}
