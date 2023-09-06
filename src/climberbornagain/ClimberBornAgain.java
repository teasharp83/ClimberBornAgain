/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package climberbornagain;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Teagan
 */
public class ClimberBornAgain extends JFrame {
    
    //gameplay/event related declarations
    private final Player player;
    private int choiceFalseCount, standardFalseCount;
    private BattleEvent currentBattleEvent;
    private int swordsmanBattleDay = 3;
    private String battleGraphic;
    
    //graphic related item declarations
    private final JTextArea graphicArea, battleArea;
    private final JPanel graphicPanel, actionPanel, gamePanel, blankPanel1, blankPanel2;
    private final GridLayout buttonLayout, graphicLayout;
    
    //all game setup button 
    private final JButton creditsButton, startButton, scoresButton, backButton, character1Button, character2Button, easyButton, normalButton, hardButton, climbButton, nextButton, standardEventButton;
    
    private Boolean noEvent;
    
    //all choice button declarations
    private final JButton leftButton, rightButton, takeButton, retrieveButton, jumpButton, fishButton, door1Button, door2Button, door3Button, iceButton, rockButton, eatButton;
    
    //battle menu button declarations
    private final JButton battleContinueButton, fightButton, spellsButton, runButton, cancelButton;
    
    //spell button declarations
    private final JButton windButton, boltButton, fireballButton, phantomButton, freezeButton, earthButton, healButton;
    
    //attack button declarations
    private final JButton sliceButton, jabButton, slashButton;
    
    //forced enemy defeated declarations
    private Boolean swordsmanDefeated, jerryDefeated, robotDefeated, dragonDefeated;
    
    //misc battle declarations
    private JLabel battleLabel;
    private final JButton dragonContinueButton;
    private final JButton endGameButton;
    private JTextField nameField;
    private final JButton enterNameButton;
    
    //misc other declarations
    private final ArrayList<JButton> spells = new ArrayList<>();
    private final ArrayList<BattleEvent> forcedBattleEvents = new ArrayList<>();
    private final ArrayList<BattleEvent> randomBattleEvents = new ArrayList<>();
    private final ArrayList<ChoiceEvent> choiceEvents = new ArrayList<>();
    private final ArrayList<StandardEvent> standardEvents = new ArrayList<>();
    private final Timer timer;
    private final Random random;
    String nothingText;
    
    public ClimberBornAgain() {
        
        //setting values to the gameplay/event declarations
        super("Climber: Born Again");
        setResizable(false);
        
        player = new Player(" "," ",1,0);
        timer = new Timer();
        random = new Random();
        nothingText = "";
        noEvent = true;
        
        //blank battle graphic, filled in by each action to create animation
        battleGraphic = """
                            %s                          |
                            %s
                            %s             %s
                            %s           %s%s
                            %s          %s%s|\\
                            %s             / \\
                            %s
                            %s
                                          HP: %s
                                        Mana: %s
                        """;
        
        //clearing event lists, not nessesary but just a precaution
        forcedBattleEvents.clear();
        randomBattleEvents.clear();
        choiceEvents.clear();
        standardEvents.clear();
        
        //counts to reset the random events, if need be
        choiceFalseCount = 0;
        standardFalseCount = 0;
        
        //defeat checks for forced battles
        swordsmanDefeated = false;
        jerryDefeated = false;
        robotDefeated = false;
        dragonDefeated = false;
        
        //blank panels used for spacing buttons
        blankPanel1 = new JPanel();
        blankPanel1.setBackground(Color.WHITE);
        blankPanel2 = new JPanel();
        blankPanel2.setBackground(Color.WHITE);
        
        //misc definitions
        graphicArea = new JTextArea();
        graphicPanel = new JPanel();
        Font graphicFont = new Font(Font.MONOSPACED, Font.PLAIN, 22);
        graphicLayout = new GridLayout();
        graphicPanel.setLayout(graphicLayout);
        graphicArea.setFont(graphicFont);
        graphicArea.setEditable(false);
        graphicPanel.add(graphicArea);
        graphicPanel.setBackground(Color.WHITE);
        battleLabel = new JLabel("");
        battleLabel.setFont(graphicFont);
        
        battleArea = new JTextArea();
        battleArea.setEditable(false);
        battleArea.setFont(graphicFont);
        
        actionPanel = new JPanel();
        gamePanel = new JPanel();
        creditsButton = new JButton("     Credits     ");
        startButton = new JButton("Start");
        scoresButton = new JButton("Scores");
        
        buttonLayout = new GridLayout();
        actionPanel.setLayout(buttonLayout);
        actionPanel.add(creditsButton);
        actionPanel.add(blankPanel1);
        actionPanel.add(startButton);
        actionPanel.add(blankPanel2);
        actionPanel.add(scoresButton);
        actionPanel.setBackground(Color.WHITE);
        
        TitleScreen();
        
        gamePanel.add(graphicPanel);
        gamePanel.add(actionPanel);
        gamePanel.setBackground(Color.WHITE);
        add(gamePanel);
        
        //button to set the credits screen
        creditsButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graphicArea.setText("""
                                                                           
                                                        Climber:
                                                       Born Again
                            
                                                    Game created by:
                                                      Teagan Sharp
                            
                                                   Special Thanks to:
                                                       Mrs.Yurky
                                                       Al Verbanec
                                                                                           |
                            """);
                actionPanel.removeAll();
                actionPanel.add(backButton);
                actionPanel.revalidate();
            }
        });
        
        //button to set the select character screen
        startButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graphicArea.setText("""
                            
                            
                            
                                                  Choose your character:
                            
                                                      O          /O\\
                                                     /|\\         /|\\
                                                     / \\         / \\ 
                            
                            
                                                                                            | 
                            """);
                actionPanel.removeAll();
                actionPanel.add(character1Button);
                actionPanel.add(blankPanel1);
                actionPanel.add(character2Button);
                actionPanel.revalidate();
            }
        });
        
        //button to set the scores screen
        scoresButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateScoresTable();
                actionPanel.removeAll();
                actionPanel.add(backButton);
                actionPanel.revalidate();
            }
        });
        
        //button to go back to title screen
        backButton = new JButton("      Back      ");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graphicPanel.removeAll();
                graphicPanel.add(graphicArea);
                actionPanel.removeAll();
                TitleScreen();
            }
        });
        
        //sets male character
        character1Button = new JButton("O");
        character1Button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                player.setCharacter(" O ");
                DifficultySelect();
            }
        });
        
        //sets female character
        character2Button = new JButton("     /O\\     ");
        character2Button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                player.setCharacter("/O\\");
                DifficultySelect();
            }
        });
        
        //sets difficulty to easy
        easyButton = new JButton("      Easy      ");
        easyButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                player.setDifficulty(0);
                CreateBattleEvents();
                StartScreen();
            }
        });
        
        //sets difficulty to normal
        normalButton = new JButton("Normal");
        normalButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                player.setDifficulty(1);
                CreateBattleEvents();
                StartScreen();
            }
        });
        
        //sets difficulty to hard
        hardButton = new JButton("Hard");
        hardButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                player.setDifficulty(2);
                CreateBattleEvents();
                StartScreen();
            }
        });
        
        //begins the game
        climbButton = new JButton("  Climb!  ");
        climbButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BeginningOfDay();
            }
        });
        
        //continues to next day
        standardEventButton = new JButton(" Continue ");
        standardEventButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graphicPanel.remove(battleArea);
                BeginningOfDay();
            }
        });
        
        //button to eat extra ration on special choice event
        eatButton = new JButton("Eat");
        eatButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                player.reduceHP(-25);
                player.reduceMana(-15);
                graphicArea.setText(String.format(nothingText, player.getCharacter(), setWidth(player.getHP(),3), setWidth(player.getMana(),3), """
                                                                                                                        You eat an extra food ration.
                                                                                                                          
                                                                                                                        """,player.getFood(), player.getHeight(), player.getDay()));
                EventActionPanelSelector(1);
            }
        });
        
        //starts dragon battle
        dragonContinueButton = new JButton(" Continue ");
        dragonContinueButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimerTask dragon9 = new TimerTask() {
                    @Override
                    public void run() {
                        currentBattleEvent = forcedBattleEvents.get(3);
                        graphicArea.setText(String.format(currentBattleEvent.getGraphic(),player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                        EventActionPanelSelector(7);
                        player.setHP(100);
                        player.setMana(100);
                        battleGraphic = """
                                            %s               %s      |
                                            %s                \\|/ 
                                            %s            %sO /   
                                            %s_i___
                                            %s       %s%s / \\  |O o  |
                                            %s      %s%s|\\      |_W___|
                                            %s         / \\     ɔ/| ɔ/|
                                            %s           %s 0    |___|
                                            %s  HP: %s %s%s|\\    Y Y
                                              Mana: %s    / \\
                                        """;
                        
                    }
                };
                TimerTask dragon8 = new TimerTask() {
                    @Override
                    public void run() {
                        battleLabel.setText("join you to help defeat it!");
                        timer.schedule(dragon9, 1500);
                    }
                };
                TimerTask dragon7 = new TimerTask() {
                    @Override
                    public void run() {
                        graphicArea.setText(String.format("""
                                                                        ___                                    o 0 O      |
                                                          ___         _/  ò\\______                              \\|/  
                                                             \\_    __/   _________\\                           O /   
                                                               \\__/_    / VVVVVVVV                           /|    _i___
                                                                \\      |                                %s  / \\  |O o  |
                                                          \\_            \\_ɅɅɅɅɅɅɅɅ_                     /|\\       |_W___|
                                                             ___    ______________/                     / \\      ɔ/| ɔ/|
                                                            /  /   /                                          0    |___|
                                                          _/   \\   \\__                                      \\/|\\    Y Y
                                                                \\_____K                                      / \\
                                            
                                                          """, player.getCharacter()));
                        battleLabel.setText("and the Robot");
                        timer.schedule(dragon8, 2000);
                    }
                };
                TimerTask dragon6 = new TimerTask() {
                    @Override
                    public void run() {
                        graphicArea.setText(String.format("""
                                                                        ___                                    o 0 O      |
                                                          ___         _/  ò\\______                              \\|/  
                                                             \\_    __/   _________\\                           O /   
                                                               \\__/_    / VVVVVVVV                           /|    
                                                                \\      |                                %s  / \\  
                                                          \\_            \\_ɅɅɅɅɅɅɅɅ_                     /|\\       
                                                             ___    ______________/                     / \\      
                                                            /  /   /                                          0
                                                          _/   \\   \\__                                      \\/|\\
                                                                \\_____K                                      / \\
                                            
                                                          """, player.getCharacter()));
                        battleLabel.setText("Jerry the Balloon Man,");
                        timer.schedule(dragon7, 2000);
                    }
                };
                TimerTask dragon5 = new TimerTask() {
                    @Override
                    public void run() {
                        graphicArea.setText(String.format("""
                                                                        ___                                               |
                                                          ___         _/  ò\\______                               
                                                             \\_    __/   _________\\                            
                                                               \\__/_    / VVVVVVVV                           
                                                                \\      |                                %s   
                                                          \\_            \\_ɅɅɅɅɅɅɅɅ_                     /|\\      
                                                             ___    ______________/                     / \\      
                                                            /  /   /                                          0    
                                                          _/   \\   \\__                                      \\/|\\
                                                                \\_____K                                      / \\
                                            
                                                          """, player.getCharacter()));
                        battleLabel.setText("The swordsman,");
                        timer.schedule(dragon6, 2000);
                    }
                };
                TimerTask dragon4 = new TimerTask() {
                    @Override
                    public void run() {
                        battleLabel.setText("...but you're not alone...");
                        timer.schedule(dragon5, 2500);
                    }
                };
                TimerTask dragon3 = new TimerTask() {
                    @Override
                    public void run() {
                        battleLabel.setText("You have no choice but to fight it...");
                        timer.schedule(dragon4, 2500);
                    }
                };
                TimerTask dragon2 = new TimerTask() {
                    @Override
                    public void run() {
                        graphicArea.setText(String.format("""
                                                                        ___                                               |
                                                          ___         _/  ò\\______                               
                                                             \\_    __/   _________\\                              
                                                               \\__/_    / VVVVVVVV                               
                                                                \\      |                                %s    
                                                          \\_            \\_ɅɅɅɅɅɅɅɅ_                     /|\\       
                                                             ___    ______________/                     / \\      
                                                            /  /   /                                               
                                                          _/   \\   \\__                                       
                                                                \\_____K                                       
                                            
                                                          """, player.getCharacter()));
                        battleLabel.setText("A DRAGON APPEARS!!");
                        timer.schedule(dragon3, 3000);
                    }
                };
                TimerTask dragon1 = new TimerTask() {
                    @Override
                    public void run() {
                        graphicArea.setText(String.format("""
                                                                        \\    /                                           |
                                                                         \\  /     O. `,:0.@ 'o.`,:
                                                                          \\/    ` @ ,         o *o;`  
                                                                           \\   O., *; BOOM!!! 0'`,:0.*   \\%s
                                                                            \\ .o *o`;         . `,:0.*@   /\\
                                                                               @.'o.`0 :0.* o`;.@ ,. Q   /\\ 
                                                                                 \\                 /
                                                                                _/                 \\_
                                                                              _/                     \\_
                                                                            _/                         \\_
                                                                           /                             \\
                                                          """, player.getCharacter()));
                        timer.schedule(dragon2, 1500);
                    }
                };
                actionPanel.removeAll();
                actionPanel.add(battleLabel);
                graphicArea.setText(String.format("""
                                                                                 _____                           |
                                                                                |     |
                                                                                |_____| 
                                                                                | \\%s
                                                                                |   |\\ 
                                                                            ____|__/_\\__ 
                                                                          _/             \\_ 
                                                                        _/                 \\_
                                                                      _/                     \\_
                                                                    _/                         \\_
                                                                   /                             \\
                                                  """, player.getCharacter()));
                battleLabel.setText("Suddenly...");
                timer.schedule(dragon1, 1000);
            }
        });
        
        nameField = new JTextField();
        
        endGameButton = new JButton(" Continue ");
        endGameButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int score = ((100-player.getDay())*100)+(player.getFood()*50)+(player.getHP()*10)+(player.getMana()*5)+(player.getDifficulty()*250);
                graphicPanel.remove(battleArea);
                String scoreTally = """
                                                                                                    |
                                                       ( 100 - Days ) * 100 = %s
                                                                Food  *  50 = %s
                                                                  HP  *  10 = %s
                                                                Mana  *   5 = %s
                                                           Difficulty Bonus = %s
                                                    +          %s
                                                   ___________________________________
                                                               Final Score = %s
                                                               
                                                     Enter your first and last name:    
                                    """;
                if (dragonDefeated == true) {
                    score += 1500;
                    graphicArea.setText(String.format(scoreTally, setWidth(((100-player.getDay())*100),4), setWidth((player.getFood()*50),4), setWidth((player.getHP()*10),4), setWidth((player.getMana()*5),4), setWidth((player.getDifficulty()*250),4), "Dragon Bonus = 1500", setWidth(score,5)));
                }
                else {
                    graphicArea.setText(String.format(scoreTally, setWidth(((100-player.getDay())*100),4), setWidth((player.getFood()*50),4), setWidth((player.getHP()*10),4), setWidth((player.getMana()*5),4), setWidth((player.getDifficulty()*250),4), "", setWidth(score,5)));
                }
                player.setScore(score);
                actionPanel.removeAll();
                actionPanel.add(nameField);
                actionPanel.add(enterNameButton);
            }
        });
        
        //enters name and calls database
        enterNameButton = new JButton(" Confirm ");
        enterNameButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                player.setName(nameField.getText());
                HighScoreQueries.updateScores(player);
                CreateScoresTable();
                actionPanel.removeAll();
                actionPanel.add(battleLabel);
                ArrayList<Player> scores = HighScoreQueries.getHighScores();
                int scoreNumber = 1;
                for (int i = 0; i < scores.size(); i++) {
                    if (scores.get(i).getName().equals(player.getName())) {
                        String row = """
                                     %s | %s | %s | %s
                                     """;
                        String rowElement1 = "  ";
                        if (player.getScore() == scores.get(i).getScore()) {
                            rowElement1 = setWidth(scoreNumber,2);
                        }
                        rowElement1 += "  " + scores.get(i).getCharacter() + "    ";
                        String rowElement2 = player.getName();
                        while (rowElement2.length() < 25) {
                            rowElement2 = rowElement2 + " ";
                        }
                        if (rowElement2.length() > 25) {
                            rowElement2 = rowElement2.substring(0,24);
                        }
                        String rowElement3 = "";
                        switch (scores.get(i).getDifficulty()) {
                            case 0 -> {
                                rowElement3 += "Easy      ";
                            }
                            case 1 -> {
                                rowElement3 += "Normal    ";
                            }
                            case 2 -> {
                                rowElement3= "Hard      ";
                            }
                        }
                        String rowElement4 = setWidth(scores.get(i).getScore(),5);
                        battleLabel.setText(String.format(row, rowElement1, rowElement2, rowElement3, rowElement4));
                    }
                    else {
                        scoreNumber += 1;
                    }
                }
            }
        });
        
        //battle buttons
        fightButton = new JButton("   Fight   ");
        fightButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPanel.removeAll();
                actionPanel.add(sliceButton);
                actionPanel.add(jabButton);
                actionPanel.add(slashButton);
                actionPanel.add(blankPanel1);
                actionPanel.add(cancelButton);
                actionPanel.revalidate();
            }
        });
        
        spellsButton = new JButton("Spells");
        spellsButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EventActionPanelSelector(10);
            }
        });
        
        runButton = new JButton("Run");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimerTask endBattle = new TimerTask() {
                    @Override
                    public void run() {
                        if ("Dragon".equals(currentBattleEvent.getName())) {
                            actionPanel.removeAll();
                            actionPanel.add(endGameButton);
                        }
                        else {
                            EventActionPanelSelector(1);
                        }
                    }
                };
                actionPanel.removeAll();
                actionPanel.add(battleLabel);
                if ("Dragon".equals(currentBattleEvent.getName())) {
                    YouCantRun();
                }
                else {
                    battleArea.setText("""
                                                                       |     










                                       """);
                    battleLabel.setText("You retreat 150ft down the mountain.");
                    player.reduceHeight(-150);
                    timer.schedule(endBattle, 2000);
                    if (forcedBattleEvents.contains(currentBattleEvent)) {
                        currentBattleEvent.setStatus(false);
                        if ("Jerry the Balloon Man".equals(currentBattleEvent.getName())) {
                            choiceEvents.get(6).setStatus(true);
                        }
                        if ("Robot".equals(currentBattleEvent.getName())) {
                            randomBattleEvents.get(2).setStatus(true);
                        }
                    }
                }
            }
        });
        
        cancelButton = new JButton("   Cancel   ");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (player.getDay() == swordsmanBattleDay) {
                    EventActionPanelSelector(8);
                }
                else {
                    EventActionPanelSelector(9);
                }
            }
        });
        
        sliceButton = new JButton("Slice");
        sliceButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPanel.removeAll();
                actionPanel.add(battleLabel);
                battleLabel.setText("You used Slice!");
                if ("Dragon".equals(currentBattleEvent.getName())) {
                    battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", "               /|    ", "  ", "  ", player.getCharacter(), "  ", " (", "\\/", "  ", "  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                }
                else {
                    battleArea.setText(String.format(battleGraphic, "  ", "  ", "  ", "  ", "  ", "  ", player.getCharacter(), "  ", " (", "\\/", "  ", "  ", "  ", setWidth(player.getHP(),3), setWidth(player.getMana(),3)));
                }
                player.reduceMana(-5);
                AttackReset();
                currentBattleEvent.reduceHP(currentBattleEvent.getSliceDamage());
            }
        });
        
        jabButton = new JButton("Jab");
        jabButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPanel.removeAll();
                actionPanel.add(battleLabel);
                battleLabel.setText("You used Jab!");
                if ("Dragon".equals(currentBattleEvent.getName())) {
                    battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", "               /|    ", "  ", "  ", player.getCharacter(), "  ", "  ", "_/", "  ", "  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                }
                else {
                    battleArea.setText(String.format(battleGraphic, "  ", "  ", "  ", "  ", "  ", "  ", player.getCharacter(), "  ", "  ","_/", "  ", "  ", "  ", setWidth(player.getHP(),3), setWidth(player.getMana(),3)));
                }
                player.reduceMana(-5);
                AttackReset();
                currentBattleEvent.reduceHP(currentBattleEvent.getJabDamage());
            }
        });
        
        slashButton = new JButton("Slash");
        slashButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPanel.removeAll();
                actionPanel.add(battleLabel);
                battleLabel.setText("You used Slash!");
                if ("Dragon".equals(currentBattleEvent.getName())) {
                    battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", "               /|    ", "  ", "/\\", player.getCharacter(), "  ", "  ", "  ", "  ", "  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                }
                else {
                    battleArea.setText(String.format(battleGraphic, "  ", "  ", "  ", "  ", "  ", "/\\", player.getCharacter(), "  ", "  ", "  ", "  ", "  ", "  ", setWidth(player.getHP(),3), setWidth(player.getMana(),3)));
                }
                player.reduceMana(-5);
                AttackReset();
                currentBattleEvent.reduceHP(currentBattleEvent.getSlashDamage());
            }
        });
        
        windButton = new JButton("Wind-15");
        windButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int cost = 15;
                actionPanel.removeAll();
                actionPanel.add(battleLabel);
                if (player.getMana() >= cost) {
                    battleLabel.setText("You used Wind!");
                    if ("Dragon".equals(currentBattleEvent.getName())) {
                        battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", " (             /|    ", "( ", " _", player.getCharacter(), " (", "  ", "  ", "  ", "  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                    }
                    else {
                        battleArea.setText(String.format(battleGraphic, "  ", "  ", " (", "  ", "( ", " _", player.getCharacter(), " (", "  ", "  ", "  ", "  ", "  ", setWidth(player.getHP(),3), setWidth(player.getMana(),3)));
                    }
                    player.reduceMana(cost);
                    AttackReset();
                    if (null == currentBattleEvent.getName()) {
                        currentBattleEvent.reduceHP(25);
                    }
                    else switch (currentBattleEvent.getName()) {
                        case "Ghost" -> currentBattleEvent.reduceHP(60);
                        case "Wizard" -> currentBattleEvent.reduceHP(15);
                        default -> currentBattleEvent.reduceHP(25);
                    }
                }
                else {
                    NotEnoughMana();
                }
            }
        });
        
        boltButton = new JButton("Bolt-65");
        boltButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int cost = 65;
                actionPanel.removeAll();
                actionPanel.add(battleLabel);
                if (player.getMana() >= cost) {
                    battleLabel.setText("You used Bolt!");
                    if ("Dragon".equals(currentBattleEvent.getName())) {
                        battleArea.setText(String.format(battleGraphic, "/ ", "o 0 O", "\\ ", "/ ", "  ", "\\              /|    ", "/ ", " _", player.getCharacter(), "\\ ", "  ", "  ", "/ ", "\\ ", "  ", "/ ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                    }
                    else {
                        battleArea.setText(String.format(battleGraphic, "/ ", "\\ ", "/ ", "  ", "\\ ", " _", player.getCharacter(), "/ ", "  ", "  ", "\\ ", "/ ", "\\ ", setWidth(player.getHP(),3), setWidth(player.getMana(),3)));
                    }
                        player.reduceMana(cost);
                    AttackReset();
                    if (null == currentBattleEvent.getName()) {
                        currentBattleEvent.reduceHP(70);
                    }
                    else switch (currentBattleEvent.getName()) {
                        case "Yeti" -> currentBattleEvent.reduceHP(100);
                        case "Ghost" -> currentBattleEvent.reduceHP(35);
                        default -> currentBattleEvent.reduceHP(70);
                    }
                }
                else {
                    NotEnoughMana();
                }
            }
        });
        
        fireballButton = new JButton("Fireball-40");
        fireballButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int cost = 40;
                actionPanel.removeAll();
                actionPanel.add(battleLabel);
                if (player.getMana() >= cost) {
                    battleLabel.setText("You used Fireball!");
                    if ("Dragon".equals(currentBattleEvent.getName())) {
                        battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", "               /|    ", "O>", " _", player.getCharacter(), "  ", "  ", "  ", "  ", "  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                    }
                    else {
                        battleArea.setText(String.format(battleGraphic, "  ", "  ", "  ", "  ", "O>", " _", player.getCharacter(), "  ", "  ", "  ", "  ", "  ", "  ", setWidth(player.getHP(),3), setWidth(player.getMana(),3)));
                    }
                    player.reduceMana(cost);
                    AttackReset();
                    if (null == currentBattleEvent.getName()) {
                        currentBattleEvent.reduceHP(50);
                    }
                    else switch (currentBattleEvent.getName()) {
                        case "Robot" -> currentBattleEvent.reduceHP(70);
                        case "Jerry the Balloon Man" -> currentBattleEvent.reduceHP(30);
                        default -> currentBattleEvent.reduceHP(50);
                    }
                }
                else {
                    NotEnoughMana();
                }
            }
        });
        
        phantomButton = new JButton("Phantom-55");
        phantomButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int cost = 55;
                actionPanel.removeAll();
                actionPanel.add(battleLabel);
                if (player.getMana() >= cost) {
                    battleLabel.setText("You used Phantom!");
                    if ("Dragon".equals(currentBattleEvent.getName())) {
                        battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", "_O             /|    ", "/|", " _", player.getCharacter(), " V", "  ", "  ", "  ", "  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                    }
                    else {
                        battleArea.setText(String.format(battleGraphic, "  ", "  ", "_O", "  ", "/|", " _", player.getCharacter(), " V", "  ", "  ", "  ", "  ", "  ", setWidth(player.getHP(),3), setWidth(player.getMana(),3)));
                    }
                    player.reduceMana(cost);
                    AttackReset();
                    if (null == currentBattleEvent.getName()) {
                        currentBattleEvent.reduceHP(60);
                    }
                    else switch (currentBattleEvent.getName()) {
                        case "Wizard" -> currentBattleEvent.reduceHP(90);
                        case "Luchador" -> currentBattleEvent.reduceHP(40);
                        default -> currentBattleEvent.reduceHP(60);
                    }
                }
                else {
                    NotEnoughMana();
                }
            }
        });
        
        freezeButton = new JButton("Freeze-25");
        freezeButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int cost = 25;
                actionPanel.removeAll();
                actionPanel.add(battleLabel);
                if (player.getMana() >= cost) {
                    battleLabel.setText("You used Freeze!");
                    if ("Dragon".equals(currentBattleEvent.getName())) {
                        battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "**", "**", "  ", "**             /|    ", "**", " _", player.getCharacter(), "**", "  ", "  ", "**", "  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                    }
                    else {
                        battleArea.setText(String.format(battleGraphic, "  ", "**", "**", "  ", "**", " _", player.getCharacter(), "**", "  ", "  ", "**", "**", "  ", setWidth(player.getHP(),3), setWidth(player.getMana(),3)));
                    }
                    player.reduceMana(cost);
                    AttackReset();
                    if (null == currentBattleEvent.getName()) {
                        currentBattleEvent.reduceHP(35);
                    }
                    else switch (currentBattleEvent.getName()) {
                        case "Dragon" -> currentBattleEvent.reduceHP(65);
                        case "Yeti" -> currentBattleEvent.reduceHP(20);
                        default -> currentBattleEvent.reduceHP(35);
                    }
                }
                else {
                    NotEnoughMana();
                }
            }
        });
        
        earthButton = new JButton("Earth-30");
        earthButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int cost = 30;
                actionPanel.removeAll();
                actionPanel.add(battleLabel);
                if (player.getMana() >= cost) {
                    battleLabel.setText("You used Earth!");
                    if ("Dragon".equals(currentBattleEvent.getName())) {
                        battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", "*o             /|    ", "/\\", " _", player.getCharacter(), "\\/", "  ", "  ", "0.","  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                    }
                    else {
                        battleArea.setText(String.format(battleGraphic, "  ", "  ", "*o", "  ", "/\\", " _", player.getCharacter(), "\\/", "  ", "  ", "0.", "  ", "  ", setWidth(player.getHP(),3), setWidth(player.getMana(),3)));
                    }
                    player.reduceMana(cost);
                    AttackReset();
                    if (null == currentBattleEvent.getName()) {
                        currentBattleEvent.reduceHP(45);
                    }
                    else switch (currentBattleEvent.getName()) {
                        case "Jester" -> currentBattleEvent.reduceHP(70);
                        case "Wizard" -> currentBattleEvent.reduceHP(20);
                        default -> currentBattleEvent.reduceHP(40);
                    }
                }
                else {
                    NotEnoughMana();
                }
            }
        });
        
        healButton = new JButton("Heal - 85");
        healButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int cost = 85;
                actionPanel.removeAll();
                actionPanel.add(battleLabel);
                if (player.getMana() >= cost) {
                    battleLabel.setText("You used Heal!");
                    if ("Dragon".equals(currentBattleEvent.getName())) {
                        battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", "            +  /|    ", "  ", " _", player.getCharacter(), "  ", "  ", "  ", "  ","  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                    }
                    else {
                        battleArea.setText(String.format(battleGraphic, "  ", "  ", "  ", " +", "  ", "  ", player.getCharacter(), "  ", "  ", " /", "  ", "  ", "  ", setWidth(player.getHP(),3), setWidth(player.getMana(),3)));
                    }
                    player.reduceMana(cost);
                    AttackReset();
                    player.setHP(100);
                }
                else {
                    NotEnoughMana();
                }
            }
        });
        
        //choice events
        ChoiceEvent splitPath = new ChoiceEvent("""
                                                                                                                |
                                                                          \\     V     /
                                                                           \\         / 
                                                                            \\       / 
                                                                             | %s |
                                                                             | /|\\ |
                                                                             | / \\ |
                                                
                                                  During your climb you come across a fork in the road.
                                                
                                                         Food: %s          Height: %s          Day: %s
                                                """, true, 2);
        choiceEvents.add(splitPath);
        String pathAnimationGraphic = """
                                                                                                      |
                                                                \\     V     /
                                                                 \\   %s   / 
                                                                  \\  /|\\  / 
                                                                   | / \\ |
                                                                   |     |
                                                                   |     |
                                      
                                      
                                      
                                               Food: %s          Height: %s          Day: %s
                                      """;
        leftButton = new JButton("Left");
        leftButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPanel.removeAll();
                graphicArea.setText(String.format(pathAnimationGraphic,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                TimerTask leftPath = new TimerTask() {
                    @Override
                    public void run() {
                        String leftGraphic = """
                                                                                                            |
                                                                      \\  %sV     /
                                                                       \\ /|\\     /
                                                                        \\/ \\    / 
                                                                         |     |
                                                                         |     |
                                                                         |     |

                                            """;
                        int graphicSelector = random.nextInt(2);
                        switch (graphicSelector) {
                            case 0 -> {
                                player.reduceHeight(100);
                                leftGraphic += """
                                                The path was quicker than expected! You climb an extra 100ft!

                                                       Food: %s          Height: %s          Day: %s
                                              """;
                            }
                            case 1 -> {
                                player.reduceHeight(-100);
                                leftGraphic += """
                                                The path was slower than expected... You lose 100ft...

                                                       Food: %s          Height: %s          Day: %s
                                              """;
                            }
                        }
                        graphicArea.setText(String.format(leftGraphic,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                        EventActionPanelSelector(1);
                    }
                };
                timer.schedule(leftPath, 600);
            }
        });
        rightButton = new JButton(" Right ");
        rightButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPanel.removeAll();
                graphicArea.setText(String.format(pathAnimationGraphic,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                TimerTask rightPath = new TimerTask() {
                    @Override
                    public void run() {
                        String rightGraphic = """
                                                                                                            |
                                                                      \\     V%s  /
                                                                       \\     /|\\ /
                                                                        \\    / \\/ 
                                                                         |     |
                                                                         |     |
                                                                         |     |

                                            """;
                         int graphicSelector = random.nextInt(2);
                         switch (graphicSelector) {
                             case 0 -> {
                                 player.reduceHeight(300);
                                 rightGraphic += """
                                                 The path was quicker than expected! You climb an extra 300ft!

                                                        Food: %s          Height: %s          Day: %s
                                               """;
                             }
                             case 1 -> {
                                 player.reduceHeight(-300);
                                 rightGraphic += """
                                                 The path was slower than expected... You lose 300ft...

                                                        Food: %s          Height: %s          Day: %s
                                               """;
                             }
                         }
                         graphicArea.setText(String.format(rightGraphic,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                         EventActionPanelSelector(1);
                    }
                };
                timer.schedule(rightPath, 600); 
            }
        });
        
        ChoiceEvent berryTree = new ChoiceEvent("""
                                                                          ___                                   |
                                                                         /. .\\ 
                                                                        |. ...| 
                                                                         \\| |/     %s 
                                                                          | |      /|\\ 
                                                                       ___|_|______/_\\____
                                                
                                                  During your climb you find a tree growing wild berries, but
                                                  your survival journal suggests they could be poisonous. 
                                                
                                                         Food: %s          Height: %s          Day: %s
                                                """, true, 3);
        choiceEvents.add(berryTree);
        takeButton = new JButton("Take");
        takeButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPanel.removeAll();
                graphicArea.setText(String.format("""
                                                                            ___                                   |
                                                                           /. .\\ 
                                                                          |. ...| 
                                                                           \\| |/\\%s 
                                                                            | |   |\\ 
                                                                         ___|_|__/_\\________
                                    
                                    
                                    
                                    
                                                           Food: %s          Height: %s          Day: %s
                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                TimerTask berries = new TimerTask() {
                    @Override
                    public void run() {
                        String takeString = """
                                                                      ___                                   |
                                                                     /. .\\ 
                                                                    |. ...| 
                                                                     \\| |/ %s 
                                                                      | | ./|\\
                                                                   ___|_|../_\\________
                                            
                                            """;
                        int graphicSelector = random.nextInt(2);
                        switch (graphicSelector) {
                            case 0 -> {
                                player.reduceFood(-1);
                                takeString += """
                                                The berries end up not being poisonous! You gain 1 food
                                                ration!

                                                       Food: %s          Height: %s          Day: %s
                                              """;
                            }
                            case 1 -> {
                                player.reduceFood(2);
                                takeString += """
                                                The berries end up being poisonous, and their juice gets all
                                                over 2 of your food rations!

                                                       Food: %s          Height: %s          Day: %s
                                              """;
                            }
                        }
                        graphicArea.setText(String.format(takeString,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                    }
                };
                timer.schedule(berries, 600);
                EventActionPanelSelector(1);
            }
        });
        
        ChoiceEvent ledge = new ChoiceEvent("""
                                                                                                            |
                                                                         %s
                                                                     \\_  /|\\
                                                                       \\_/_\\___o__
                                                                              Z__/
                                                                     _________/
                                            
                                              During your climb you find a discarded food ration on the
                                              edge of a cliff.
                                            
                                                     Food: %s          Height: %s          Day: %s
                                            """, true, 4);
        choiceEvents.add(ledge);
        retrieveButton = new JButton("Retrieve");
        retrieveButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPanel.removeAll();
                graphicArea.setText(String.format("""
                                                                                                         |
                                                                         %s
                                                                  \\_     /|\\
                                                                    \\____/_\\o__
                                                                           Z__/
                                                                  _________/
                                         
                                           You are able to retrieve the ration, and you add it to your
                                           bag.
                                         
                                                  Food: %s          Height: %s          Day: %s
                                         """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                TimerTask cliff = new TimerTask() {
                    @Override
                    public void run() {
                        int graphicSelector = random.nextInt(2);
                        switch (graphicSelector) {
                            case 0 -> {
                                player.reduceHeight(-150);
                                graphicArea.setText(String.format("""
                                                                                                                 |

                                                                          \\_         _%s
                                                                            \\_______ _/|
                                                                                   Z  | o__
                                                                          _________/ .  Z_/

                                                   As you reach down to grab the ration, the ledge breaks and
                                                   you tumble 150ft, losing the ration in the process.

                                                          Food: %s          Height: %s          Day: %s
                                                 """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                            }
                            case 1 -> {
                                player.reduceFood(-1);
                                graphicArea.setText(String.format("""
                                                                                                                 |
                                                                                 %s
                                                                          \\_     /|\\o
                                                                            \\____/_\\___
                                                                                   Z__/
                                                                          _________/

                                                   You are able to retrieve the ration, and you add it to your
                                                   bag.

                                                          Food: %s          Height: %s          Day: %s
                                                 """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                            }
                        }
                        EventActionPanelSelector(1);
                    }
                };
                timer.schedule(cliff, 600);
            }
        });
        
        ChoiceEvent longJump = new ChoiceEvent("""
                                                                                                               |
                                                                      %s
                                                                      /|\\
                                                                    __/_\\_           ______
                                                                          |         |
                                                                          |         |
                                               
                                                 During your climb you come across a large pit. If you make it
                                                 across, you'll be able to take a much shorter path.
                                               
                                                        Food: %s          Height: %s          Day: %s
                                               """, true, 5);
        choiceEvents.add(longJump);
        jumpButton = new JButton("Jump");
        jumpButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPanel.removeAll();
                graphicArea.setText(String.format("""
                                                                                                                  |
                                                                                 _%s
                                                                                 _/\\ 
                                                                       ______     |     ______
                                                                   That's all|         |...a leap
                                                                     it is...|         |of faith...
                             
                             
                             
                             
                                                           Food: %s          Height: %s          Day: %s
                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                TimerTask jump = new TimerTask() {
                    @Override
                    public void run() {
                        int graphicSelector = random.nextInt(2);
                        switch (graphicSelector) {
                            case 0 -> {
                                graphicArea.setText(String.format("""
                                                                                                                                  |
                                                                                                         %s
                                                                                                         /|\\
                                                                                       ______           _/_\\__
                                                                                             |         |
                                                                                             |         |
                             
                                                                    You make the jump and shorten your path by 300ft!
                             
                             
                                                                           Food: %s          Height: %s          Day: %s
                                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                            }
                            case 1 -> {
                                graphicArea.setText(String.format("""
                                                                                                                                  |
                                                                                                       
                                                                                                       
                                                                                       ______    \\ /    ______
                                                                                             |   \\|/   |
                                                                                             |   %s   |
                             
                                                                    You miss the jump and fall 150ft...
                             
                             
                                                                           Food: %s          Height: %s          Day: %s
                                                                  """,new StringBuilder(player.getCharacter()).reverse(),player.getFood(),player.getHeight(),player.getDay()));
                            }
                        }
                        EventActionPanelSelector(1);
                    }
                };
                timer.schedule(jump, 600);
            }
            
        });
        
        ChoiceEvent fishOn = new ChoiceEvent("""
                                                                                                             |
                                                                       _______
                                                                      /       \\    %s
                                                                     |         \\   /|\\ 
                                                                      \\_       /   / \\ 
                                                                        \\_____/     
                                             
                                               During your climb you stumble upon a pond filled with fish!
                                               You could fish, but it would probably make you hungry.
                                             
                                                      Food: %s          Height: %s          Day: %s
                                             """, true, 6);
        choiceEvents.add(fishOn);
        
        fishButton = new JButton("Fish On!");
        fishButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPanel.removeAll();
                graphicArea.setText(String.format("""
                                                                                                                  |
                                                                            _______
                                                                           /       \\ |\\ %s
                                                                          |         \\| \\/|\\ 
                                                                           \\_       /   / \\
                                                                             \\_____/
                                                  
                                                    
                                                    
                                                  
                                                           Food: %s          Height: %s          Day: %s
                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                
                TimerTask fish3 = new TimerTask() {
                   @Override
                   public void run() {
                       int graphicSelector = random.nextInt(3);
                       switch (graphicSelector) {
                           case 0 -> {
                                player.reduceFood(1);
                                graphicArea.setText(String.format("""
                                                                                                                                  |
                                                                                            _______
                                                                                           /       \\ |\\ %s
                                                                                          |         \\| \\/|\\ 
                                                                                           \\_       /   / \\
                                                                                             \\_____/

                                                                    You fail to catch any fish and you eat one of your food
                                                                    rations.

                                                                           Food: %s          Height: %s          Day: %s
                                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                           }
                           case 1 -> {
                                player.reduceFood(-3);
                                graphicArea.setText(String.format("""
                                                                                                                                  |
                                                                                            _______
                                                                                           /       \\ |\\ %s
                                                                                          |         \\| \\/|\\ 0
                                                                                           \\_       /0  / \\ 00
                                                                                             \\_____/

                                                                    You are able to catch four fish and eat one of your food
                                                                    rations!

                                                                           Food: %s          Height: %s          Day: %s
                                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));               
                           }
                           case 2 -> {
                                graphicArea.setText(String.format("""
                                                                                                                                  |
                                                                                            _______     
                                                                                           /       \\ |\\ %s
                                                                                          |         \\| \\/|\\ 
                                                                                           \\_       /0  / \\
                                                                                             \\_____/

                                                                    You are able to catch one fish, but you also eat on of your                                                                                                                                    
                                                                    food rations.

                                                                           Food: %s          Height: %s          Day: %s
                                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                           }
                       }
                       EventActionPanelSelector(1);
                   }
                };
                
                TimerTask fish2 = new TimerTask() {
                    @Override
                    public void run() {
                        graphicArea.setText(String.format("""
                                                                                                                          |
                                                                                    _______  _ 
                                                                                   /       \\/ \\ %s
                                                                                  |        /\\  \\/|\\ 
                                                                                   \\_     o /   / \\
                                                                                     \\_____/




                                                                   Food: %s          Height: %s          Day: %s
                                                          """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                        timer.schedule(fish3, 2000);
                   }
               };
                
                TimerTask fish1 = new TimerTask() {
                    @Override
                    public void run() {
                        graphicArea.setText(String.format("""
                                                                                                 _                        |
                                                                                    _______     / \\
                                                                                   /       \\   |%s
                                                                                  |         \\  |/|\\ 
                                                                                   \\_       /   / \\
                                                                                     \\_____/




                                                                   Food: %s          Height: %s          Day: %s
                                                          """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                        timer.schedule(fish2, 400);
                   }
               };
               timer.schedule(fish1, 600);
            }
        });
        
        ChoiceEvent gameShow = new ChoiceEvent("""
                                                                                                               |
                                               
                                                                   ___        ___        ___
                                                          0       | 1 |      | 2 |      | 3 |      %s
                                                         /|\\,     |  .|      |  .|      |  .|      /|\\
                                                      ___/_\\______|___|______|___|______|___|______/_\\___
                                               
                                                 During your climb you stumble upon a game show host! He
                                                 insists that you choose a door to win a prize!
                                               
                                                        Food: %s          Height: %s          Day: %s
                                               """, false, 11);
        choiceEvents.add(gameShow);
        
        door1Button = new JButton("  Door #1  ");
        door1Button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPanel.removeAll();
                TimerTask door1 = new TimerTask() {
                    @Override
                    public void run() {
                        int graphicSelector = random.nextInt(2);
                        switch (graphicSelector) {
                            case 0 -> {
                                player.reduceFood(2);
                                graphicArea.setText(String.format("""
                                                                                                                                  |

                                                                                  ___ ___        ___        ___
                                                                             0   |   |||||%s   | 2 |      | 3 |     
                                                                            /|\\, |.  |'o'|/|\\   |  .|      |  .|
                                                                         ___/_\\__|___|'⅄'|/_\\___|___|______|___|____________

                                                                    Behind Door #1, you find a goblin, that steals 2 of your food
                                                                    rations!

                                                                           Food: %s          Height: %s          Day: %s
                                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                            }
                            case 1 -> {
                                player.reduceFood(-3);
                                graphicArea.setText(String.format("""
                                                                                                                                  |

                                                                                  ___ ___        ___        ___
                                                                             0   |   |||||%s   | 2 |      | 3 |     
                                                                            /|\\, |.  |||||/|\\   |  .|      |  .|
                                                                         ___/_\\__|___|...|/_\\___|___|______|___|____________

                                                                    Behind Door #1, you find 3 food rations!
                                                                  

                                                                           Food: %s          Height: %s          Day: %s
                                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                            }
                        }
                        EventActionPanelSelector(1);
                    }
                };
                graphicArea.setText(String.format("""
                                                                                                                  |
                                                  
                                                                  ___ ___        ___        ___
                                                             0   |   |||||%s   | 2 |      | 3 |     
                                                            /|\\, |.  |||||/|\\   |  .|      |  .|
                                                         ___/_\\__|___|||||/_\\___|___|______|___|____________
                                                  
                                                    Behind Door #1, you find...
                                                    
                                                  
                                                           Food: %s          Height: %s          Day: %s
                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                timer.schedule(door1, 1000);
            }
        });
        
        door2Button = new JButton("Door #2");
        door2Button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPanel.removeAll();
                TimerTask door2 = new TimerTask() {
                    @Override
                    public void run() {
                        int graphicSelector = random.nextInt(2);
                        switch (graphicSelector) {
                            case 0 -> {
                                player.setHP(100);
                                player.setMana(100);
                                graphicArea.setText(String.format("""
                                                                                                                                  |

                                                                                      ___    ___ ___        ___
                                                                             0       | 1 |  |   |||||%s   | 3 |     
                                                                            /|\\,     |  .|  |.  |||||/|\\   |  .|
                                                                         ___/_\\______|___|__|___||O||/_\\___|___|____________

                                                                    Behind Door #2, you find a healing potion, which fully
                                                                    restores both your HP and Mana!

                                                                           Food: %s          Height: %s          Day: %s
                                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));    
                            }
                            case 1 -> {
                                graphicArea.setText(String.format("""
                                                                                                                                  |

                                                                                      ___    ___ ___        ___
                                                                             0       | 1 |  |   |$$$|%s   | 3 |     
                                                                            /|\\,     |  .|  |.  |$$$|/|\\   |  .|
                                                                         ___/_\\______|___|__|___|$$$|/_\\___|___|____________

                                                                    Behind Door #2, you find 1 MILLION DOLLARS! Too bad it
                                                                    doesn't help you with your climb...

                                                                           Food: %s          Height: %s          Day: %s
                                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                            }
                        }
                        EventActionPanelSelector(1);
                    }
                };
                graphicArea.setText(String.format("""
                                                                                                                  |
                                                  
                                                                      ___    ___ ___        ___
                                                             0       | 1 |  |   |||||%s   | 3 |     
                                                            /|\\,     |  .|  |.  |||||/|\\   |  .|
                                                         ___/_\\______|___|__|___|||||/_\\___|___|____________
                                                  
                                                    Behind Door #2, you find...
                                                    
                                                  
                                                           Food: %s          Height: %s          Day: %s
                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                timer.schedule(door2, 1000);
            }
        });
        
        door3Button = new JButton("Door #3");
        door3Button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPanel.removeAll();
                TimerTask door3 = new TimerTask() {
                    @Override
                    public void run() {
                        int graphicSelector = random.nextInt(2);
                        switch (graphicSelector) {
                            case 0 -> {
                                player.reduceHeight(-200);
                                graphicArea.setText(String.format("""
                                                                                                                                  |

                                                                                      ___        ___    ___ ___
                                                                             0       | 1 |      | 2 |  |   |   |    
                                                                            /|\\,     |  .|      |  .|  |.  |   | | 
                                                                         ___/_\\______|___|______|___|__|___|___||||_________
                                                                                                               \\%s/
                                                                    Behind Door #3, you find nothing! Suddenly, a trap door opens
                                                                    below you and you fall 200ft!

                                                                           Food: %s          Height: %s          Day: %s
                                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));   
                            }
                            case 1 -> {
                                player.reduceHeight(300);
                                graphicArea.setText(String.format("""
                                                                                                                                  |

                                                                                      ___        ___    ___ ___
                                                                             0       | 1 |      | 2 |  |   | H |%s   
                                                                            /|\\,     |  .|      |  .|  |.  | H |/|\\
                                                                         ___/_\\______|___|______|___|__|___|_H_|/_\\_________

                                                                    Behind Door #3, you find a ladder that you use to gain 300ft
                                                                    of progress!

                                                                           Food: %s          Height: %s          Day: %s
                                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                            }
                        }
                        EventActionPanelSelector(1);
                    }
                };
                graphicArea.setText(String.format("""
                                                                                                                  |
                                                  
                                                                      ___        ___    ___ ___
                                                             0       | 1 |      | 2 |  |   |||||%s     
                                                            /|\\,     |  .|      |  .|  |.  |||||/|\\
                                                         ___/_\\______|___|______|___|__|___|||||/_\\____________
                                                  
                                                    Behind Door #3, you find...
                                                    
                                                  
                                                           Food: %s          Height: %s          Day: %s
                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                timer.schedule(door3, 1000);
            }
        });
        
        ChoiceEvent sorcerer = new ChoiceEvent("""
                                                                                                               |
                                               
                                               
                                                                   Q                      %s  
                                                                 0/|\\0                    /|\\
                                                             _____/_\\_____________________/_\\_____
                                               
                                                 During your climb you meet a sorcerer, who offers you either
                                                 the Tome of Ice or the Tome of Rock.
                                               
                                                        Food: %s          Height: %s          Day: %s
                                               """, false, 12);
        choiceEvents.add(sorcerer);
        
        iceButton = new JButton("   Ice   ");
        iceButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPanel.removeAll();
                spells.add(freezeButton);
                TimerTask ice = new TimerTask() {
                    @Override
                    public void run() {
                        graphicArea.setText(String.format("""
                                                                                                                          |


                                                                              Q                      %s  
                                                                             /|\\0                    /|\\
                                                                        _____/_\\_____________________/_\\_____

                                                            You received the Ice Tome!


                                                                   Food: %s          Height: %s          Day: %s
                                                          """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                        EventActionPanelSelector(1);
                    }
                };
                graphicArea.setText(String.format("""
                                                                                                                  |
                                                  
                                                  
                                                                Poof! Q                      %s  
                                                                    */|\\0                    /|\\
                                                                _____/_\\_____________________/_\\_____
                                                  
                                                    
                                                    
                                                  
                                                           Food: %s          Height: %s          Day: %s
                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                timer.schedule(ice, 500);
            }
        });  
        
        rockButton = new JButton("Rock");
        rockButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPanel.removeAll();
                spells.add(earthButton);
                TimerTask rock = new TimerTask() {
                    @Override
                    public void run() {
                        graphicArea.setText(String.format("""
                                                                                                                          |


                                                                              Q                      %s  
                                                                            0/|\\                     /|\\
                                                                        _____/_\\_____________________/_\\_____

                                                            You received the Rock Tome!


                                                                   Food: %s          Height: %s          Day: %s
                                                          """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                        EventActionPanelSelector(1);
                    }
                };
                graphicArea.setText(String.format("""
                                                                                                                  |
                                                  
                                                  
                                                                      Q  Poof!               %s  
                                                                    0/|\\*                    /|\\
                                                                _____/_\\_____________________/_\\_____
                                                  
                                                    
                                                    
                                                  
                                                           Food: %s          Height: %s          Day: %s
                                                  """,player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                timer.schedule(rock, 500);
            }
        });
        
        //bad events
        StandardEvent brokenLedge = new StandardEvent("""
                                                                                                                      |
                                                                            %s/        _________ 
                                                                             /\\_  .   .| 
                                                                               \\   _ . | 
                                                                                   \\|   \\ 
                                                                                   .  .  \\ 
                                                                    
                                                       During your climb a ledge you're standing on breaks and you
                                                       fall 100ft.
                                
                                                              Food: %s          Height: %s          Day: %s
                                                    """, true, -100, 0);
        standardEvents.add(brokenLedge);
        
        StandardEvent lizard = new StandardEvent("""
                                                                                                                      %s
                                                    
                                                                              _       __ 
                                                                             ( \\_    /. \\_______ 
                                                                             |   \\   \\__/  __   \\ 
                                                                             |____|     |_|  |_|\\| 
                                                
                                                        During your climb you discover a lizard inside of your bag,
                                                        and it ate 1 of your food rations!
                                                 
                                                               Food: %s          Height: %s          Day: %s 
                                                    """, true, 0, -1);
        standardEvents.add(lizard);
        
        StandardEvent mapOrientation = new StandardEvent("""
                                                                                     _____________                         %s
                                                                                    |  \\  \\  _    |
                                                                                    |   \\  \\/ /   |
                                                                                    |    \\   /    |
                                                                                    |     \\  \\    |
                                                                                    |______\\__\\___|
                                                         
                                                             During your climb you check your map and realize you've been
                                                             traveling in the wrong direction. You lose 150ft of progress.
                                                         
                                                                    Food: %s          Height: %s          Day: %s 
                                                         """, true, -150, 0);
        standardEvents.add(mapOrientation);
        
        StandardEvent frozenWater = new StandardEvent("""
                                                                                                                        %s
                                                                                    ___||___
                                                                                   /~~~~~~~~\\ 
                                                                                  |          |
                                                                                  |          |
                                                                                   \\________/
                                                      
                                                          During your climb the water inside your flask freezes. You
                                                          lose 50ft of progress and eat an extra ration getting more.
                                                      
                                                                 Food: %s          Height: %s          Day: %s
                                                      """, true, -50, -1);
        standardEvents.add(frozenWater);
        
        StandardEvent rockslide = new StandardEvent("""
                                                                                                                    |
                                                                              \\_0.
                                                                                \\_o *\\%s
                                                                                  \\_Q_/\\ 
                                                                                    \\_|@  0
                                                                                      \\_.,  
                                                    
                                                      During your climb you get caught in a rockslide! You come out
                                                      of it relatively unharmed, but you lose 300ft of progress.
                                                    
                                                             Food: %s          Height: %s          Day: %s
                                                    """, true, -300, 0);
        standardEvents.add(rockslide);
        
        StandardEvent alienAbduction = new StandardEvent("""
                                                                                     _______                             | 
                                                                                    / o o o \\ 
                                                                                   <_________> 
                                                                                     /%s/ \\   
                                                                                    /  |\\_  \\ 
                                                                                   /    |    \\
                                                         
                                                           During your climb you are abducted by aliens! They steal 2
                                                           of your food rations to study and return you to Mt. Scatman.
                                                         
                                                                  Food: %s          Height: %s          Day: %s
                                                         """,true, 0, -2);
        standardEvents.add(alienAbduction);
        
        StandardEvent fog = new StandardEvent("""
                                                                                                              |
                                                                     ~~~~~~~~~~~~~~~~~~~
                                                                    ~~~~~~~~~~~~~~~~~~~~~
                                                                     ~~~~~~~~%s~~~~~~~~
                                                                    ~~~~~~~~ /|\\~~~~~~~~~
                                                                    _________/_\\_________
                                              
                                                During your climb you encounter a thick fog. When the fog
                                                lifts, you realize you've drifted 200ft off course.
                                              
                                                       Food: %s          Height: %s          Day: %s
                                              """, true, -200, 0);
        standardEvents.add(fog);
        
        StandardEvent thief = new StandardEvent("""
                                                                                                                  %s
                                                                       
                                                                       \\_
                                                                         \\_                O
                                                                           \\_     ____   </\\o
                                                                             \\__O/___/___/|__
                                                
                                                    During your climb you rest to prepare for the next day. As
                                                    you rest, a thief steals 3 of your food rations!
                                                
                                                           Food: %s          Height: %s          Day: %s
                                                """, true, 0, -3);
        standardEvents.add(thief);
        
        
        //good events
        StandardEvent springShoes = new StandardEvent("""
                                                                                                                      |
                                                                             \\_      \\%s/      
                                                                               \\_      |           
                                                                                 \\_   / \\           
                                                                                   \\_z   z        
                                                                                     \\_________
                                                      
                                                        During your climb you find a pair of rusty spring shoes! You
                                                        jump 300ft before they break.
                                                      
                                                               Food: %s          Height: %s          Day: %s
                                                      """, true, 300, 0);
        standardEvents.add(springShoes);
        
        StandardEvent lunch = new StandardEvent("""
                                                                                                                  %s
                                                                          _/          ()  () 
                                                                         | |  _____  ()()() ()
                                                                         |_| (     )  ()() () 
                                                                             |     | 
                                                                             |_____| 
                                                
                                                    During you climb you find another climber's lunch that was
                                                    left behind. You gain 3 food rations.
                                                    
                                                           Food: %s          Height: %s          Day: %s
                                                """, true, 0, 3);
        standardEvents.add(lunch);
        
        StandardEvent rope = new StandardEvent("""
                                                                       _____                                   | 
                                                                          _/ \\
                                                                       __/    \\
                                                                               |\\%s
                                                                                  |\\
                                                                       __________/_\\__
                                               
                                                 During your climb you find 50ft of rope abandoned by another
                                                 climber. You use it to grapple to a higher ledge. 
                                               
                                                        Food: %s          Height: %s          Day: %s
                                               """, true, 50, 0);
        standardEvents.add(rope);
        
        StandardEvent suctionCups = new StandardEvent("""
                                                                                                                      |
                                                                             |D
                                                                             |\\%s
                                                                             |D-|
                                                                             | / \\     ____ 
                                                                             |________/___/O__
                                                      
                                                        During your climb you find a pair of suction cups next to a
                                                        sleeping climber. You climb 100ft before tossing them back. 
                                                      
                                                               Food: %s          Height: %s          Day: %s
                                                      """, true, 100, 0);
        standardEvents.add(suctionCups);
        
        StandardEvent miscountingRations = new StandardEvent("""
                                                                                                                             |
                                                             
                                                             
                                                                                       %s
                                                                                       /|\\ \\ oo /  
                                                                                     __/_\\__|oo|____
                                                             
                                                               During your climb you check your food rations and realize you
                                                               miscounted originally and have 1 more ration than you thought.
                                                             
                                                                      Food: %s          Height: %s          Day: %s
                                                             """, true, 0, 1);
        standardEvents.add(miscountingRations);
        
        StandardEvent shortcut = new StandardEvent("""
                                                                            ____                                   |
                                                                           //||\\\\  
                                                                          ///||\\\\\\  
                                                                         ||||||||||   %s
                                                                         ||||||||||   /|\\ 
                                                                        _||||||||||___/_\\__
                                                   
                                                     During your climb you discover a shortcut through a cave in
                                                     the side of the mountain. It cuts 200ft off of your climb.
                                                   
                                                            Food: %s          Height: %s          Day: %s
                                                   """, true, 200, 0);
        standardEvents.add(shortcut);
        
        StandardEvent extraClimb = new StandardEvent("""
                                                                                                                     |
                                                                           _o_
                                                                              \\_
                                                                                \\_       %s
                                                                                  \\_     /|\\ 
                                                                                    \\____/_\\__
                                                     
                                                       During your climb you notice a food ration a short climb from
                                                       where to plan to stop. You climb an extra 50ft to get it. 
                                                     
                                                              Food: %s          Height: %s          Day: %s
                                                     """, true, 50, 1);
        standardEvents.add(extraClimb);
        
        StandardEvent vendingMachine = new StandardEvent("""
                                                                                                                         |
                                                              
                                                                                    ____
                                                                                   |HHHD|  %s
                                                                               \\_  |HOHH|  /|\\ 
                                                                                 \\_|HHHH|__/_\\___
                                                         
                                                           During your climb you find a rusty old vending machine! Only
                                                           two items are in stock, so you add then to your food rations. 
                                                         
                                                                  Food: %s          Height: %s          Day: %s
                                                         """, true, 0, 2);
        standardEvents.add(vendingMachine);
        
        StandardEvent anotherClimber = new StandardEvent("""
                                                                                                                         |
                                                                                       O 
                                                                                      /|\\ 
                                                                                    __/_\\\\%s
                                                                                       _/  |\\
                                                                                    __/   / \\ 
                                                         
                                                           During your climb another climber offers to give you a hand.
                                                           He helps you gain an extra 150ft of progress.
                                                         
                                                                  Food: %s          Height: %s          Day: %s
                                                         """, true, 150, 0);
        standardEvents.add(anotherClimber);
        
        //sets graphic to battle mode
        battleContinueButton = new JButton(" Continue ");
        battleContinueButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if ("Dragon".equals(currentBattleEvent.getName())) {
                    battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", "               /|    ", "  ", "  ", player.getCharacter(), "  ", "  ", "\\/", "  ","  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                }
                else {
                    battleArea.setText(String.format(battleGraphic, "  ", "  ", "  ", "  ", "  ", "  ", player.getCharacter(), "  ", "  ","\\/", "  ", "  ", "  ", setWidth(player.getHP(),3), setWidth(player.getMana(),3)));
                }
                graphicPanel.add(battleArea);
                graphicArea.setText(currentBattleEvent.getStaticBattleGraphic());
                if (player.getDay() == swordsmanBattleDay) {
                    EventActionPanelSelector(8);  
                }
                else {
                    EventActionPanelSelector(9);
                }
            }
        });
        
        //selects each day's event
        nextButton = new JButton("   Next   ");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if ((player.getHeight() <= 0) && (player.getFood() >= 0)) {
                    graphicArea.setText(String.format("""
                                                                                     _____                           |
                                                                                    |     | 
                                                                                    |_____| 
                                                                                    | \\%s
                                                                                    |   |\\ 
                                                                                ____|__/_\\__ 
                                                                              _/             \\_ 
                                                                             /                 \\
                                        
                                                        Congratulations! You have reached the summit with %s food
                                                        rations remaining. The climb took you %s days.
                                                      """,player.getCharacter(),player.getFood(),player.getDay()));
                    actionPanel.removeAll();
                    if ((swordsmanDefeated == true) && (jerryDefeated == true) && (robotDefeated == true)) {
                        actionPanel.add(dragonContinueButton);
                    }
                    else {
                        actionPanel.add(endGameButton);
                    }
                }
                else if (player.getFood() <= 0) {
                    graphicArea.setText(String.format("""
                                                                                  ________                           |
                                                                                 /        \\ 
                                                                                |  /\\  /\\  |
                                                                                |  \\/  \\/  |
                                                                                |    /\\    |
                                                                                |          |
                                                                                 \\        /
                                                                                  |_|__|_|
                                                      
                                                        You have run out of food and died on Day %s.
                                                        Remaining distance: %sft. 
                                                      """,player.getDay(),player.getHeight()));
                    actionPanel.removeAll();
                    actionPanel.add(endGameButton);
                }
                else if ((player.getDay() == swordsmanBattleDay) && (forcedBattleEvents.get(0).isStatus())) {
                    currentBattleEvent = forcedBattleEvents.get(0);
                    graphicArea.setText(String.format(currentBattleEvent.getGraphic(),player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                    EventActionPanelSelector(7);
                }
                else if ((player.getHeight() <= 7500) && (forcedBattleEvents.get(1).isStatus())) {
                    currentBattleEvent = forcedBattleEvents.get(1);
                    graphicArea.setText(String.format(currentBattleEvent.getGraphic(),player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                    EventActionPanelSelector(7);
                }
                else if ((player.getHeight() <= 3500) && (forcedBattleEvents.get(2).isStatus())) {
                    currentBattleEvent = forcedBattleEvents.get(2);
                    graphicArea.setText(String.format(currentBattleEvent.getGraphic(),player.getCharacter(),player.getFood(),player.getHeight(),player.getDay()));
                    EventActionPanelSelector(7);
                }
                else {
                    EventSelector();
                }
            }
        });
    }
    
    //creates the title screen
    private void TitleScreen() {
        graphicArea.setText("""
                                                                                           |
                            
                                      ___    __     __   _     _   ___   ___   ___
                                     /   \\  |  |   |  | | \\   / | |   \\ |  _| |   \\
                                    |   /   |  |   |  | |  \\_/  | |   / | |   |   /
                                    |  |    |  |_  |  | |       | |  <  |  >  |   \\
                                    |   \\   |    | |  | |       | |   \\ | |_  | |\\ \\
                                     \\___/  |____| |__| |_/\\_/\\_| |___/ |___| |_| \\|
                                                   B O R N  A G A I N
                            
                                                  Press Start to Begin
                            """);
        actionPanel.add(creditsButton);
        actionPanel.add(blankPanel1);
        actionPanel.add(startButton);
        actionPanel.add(blankPanel2);
        actionPanel.add(scoresButton);
        actionPanel.revalidate();
    }
    
    //creates difficulty select screen
    private void DifficultySelect() {
        String nameText = """
                            
                            
                            
                            
                                                           %s
                                                           /|\\
                                                           / \\
                            
                            
                                                 Select your difficulty:
                                                                                            |
                            """;
        graphicArea.setText(String.format(nameText, player.getCharacter()));
        actionPanel.removeAll();
        actionPanel.add(easyButton);
        actionPanel.add(blankPanel1);
        actionPanel.add(normalButton);
        actionPanel.add(blankPanel2);
        actionPanel.add(hardButton);
        actionPanel.revalidate();
    }
    
    //creates scores table
    private void CreateScoresTable() {
        ArrayList<Player> scores = HighScoreQueries.getHighScores();
        int scoreCount = 10;
        if (scores.size() < 10) {
            scoreCount = scores.size();
        }
        String scoresTable = """
                                  Character | Name                      | Difficulty | Score   |
                             """;
        for (int i = 0; i < scoreCount; i++) {
            String row = """
                            %s | %s | %s | %s
                         """;
            String rowElement1 = setWidth(i+1,2) + "  " + scores.get(i).getCharacter() + "    ";
            String rowElement2 = scores.get(i).getName();
            while (rowElement2.length() < 25) {
                rowElement2 = rowElement2 + " ";
            }
            if (rowElement2.length() > 25) {
                rowElement2 = rowElement2.substring(0,24);
            }
            String rowElement3 = "";
            switch (scores.get(i).getDifficulty()) {
                case 0 -> {
                    rowElement3 += "Easy      ";
                }
                case 1 -> {
                    rowElement3 += "Normal    ";
                }
                case 2 -> {
                    rowElement3= "Hard      ";
                }
            }
            String rowElement4 = setWidth(scores.get(i).getScore(),5);
            scoresTable += String.format(row, rowElement1, rowElement2, rowElement3, rowElement4);
        }
        while (scoreCount < 10) {
            scoresTable += """
                                          |                           |            |           
                           """;
            scoreCount += 1;
        }
        graphicArea.setText(scoresTable);
    }
    
    //creates start screen
    private void StartScreen() {
        graphicArea.setText("""
                                                                                            |
                                                           _/\\_
                                                         _/    \\_
                                                       _/        \\_
                                                     _/            \\_
                                                   _/                \\_     You
                                                 _/       12500ft      \\_    |
                                               _/                        \\_  V
                                             _/                            \\_.
                              You begin your accent to the top of Mt. Scatman. You have 20
                              food rations.
                            """);
        actionPanel.removeAll();
        actionPanel.add(climbButton);
        actionPanel.revalidate();
    }
    
    private void BeginningOfDay() {
        int distance = 600;
        String heal = "";
        String restore = "";
        if (player.getHeight() < 600) {
            distance = player.getHeight();
        }
        if (player.getHP() < 100) {
            if (player.getHP() <= 75) {
                heal = "+25 HP";
            }
            else if (player.getHP() > 75) {
                heal = String.format("+%s HP", 100-player.getHP());
            }
            player.reduceHP(-25);
        }
        if (player.getMana() < 100) {
            if (player.getMana() <= 85) {
                restore = "+15 Mana"; 
            }
            else if (player.getMana() > 85) {
                restore = String.format("+%s Mana", 100-player.getMana());
            }
            player.reduceMana(-15);
        }
        player.reduceFood(1);
        player.reduceHeight(600);
        player.increaseDay();
        actionPanel.removeAll();
        actionPanel.add(nextButton);
        String nextText = """
                                                                                          |

                                                      \\_
                                                        \\_ %s %s
                                                          \\_/\\ %s 
                                                            \\/\\                         

                            You climb %sft up the mountain and eat 1 of your food 
                            rations.

                                   Food: %s          Height: %s          Day: %s 
                          """;
        graphicArea.setText(String.format(nextText,player.getCharacter(),heal,restore,distance,setWidth(player.getFood(), 2),setWidth(player.getHeight(), 5),setWidth(player.getDay(), 2)));
    }
    
    //ensures each stat doesn't change length/nothing is displaced even if the number gets smaller
    //an altered version is used in the CreateScoresTable() function
    public static String setWidth(int stat, int length) {
       String stringStat = Integer.toString(stat); 
       while (stringStat.length() < length) {
           stringStat = " " + stringStat;
       }
       return stringStat;
    }
    
    //selects and sets up daily events
    private void EventSelector() {
        int typeSelector;
        if (player.getDay() < 3) {
            typeSelector = player.getDay() - 1;
        }
        else if (noEvent == false) {
            typeSelector = random.nextInt(3);
        }
        else {
            typeSelector = random.nextInt(4);
        }
        switch (typeSelector) {
            case 0 -> {
                if (standardFalseCount == standardEvents.size()) {
                    standardEvents.forEach((i) -> i.setStatus(true));
                    standardFalseCount = 0;
                }
                int standardEventSelector = random.nextInt(standardEvents.size());
                if (standardEvents.get(standardEventSelector).isStatus()) {
                    EventActionPanelSelector(1);
                    player.reduceFood(standardEvents.get(standardEventSelector).getFoodInfluence());
                    player.reduceHeight(standardEvents.get(standardEventSelector).getHeightInfluence());
                    graphicArea.setText(String.format(standardEvents.get(standardEventSelector).getGraphic(),player.getCharacter(),setWidth(player.getFood(), 2),setWidth(player.getHeight(), 5),setWidth(player.getDay(), 2)));
                    standardEvents.get(standardEventSelector).setStatus(false);
                    standardFalseCount += 1;
                    noEvent = true;
                }
                else {
                    EventSelector();
                }
            }
            case 1 -> {
                if (choiceFalseCount == choiceEvents.size()) {
                    choiceEvents.forEach((i) -> i.setStatus(true));
                    choiceEvents.get(6).setStatus(false);
                    choiceFalseCount = 1;
                }
                int choiceEventSelector = random.nextInt(choiceEvents.size());
                if (choiceEvents.get(choiceEventSelector).isStatus()) {
                    EventActionPanelSelector(choiceEvents.get(choiceEventSelector).getPanelNumber());
                    graphicArea.setText(String.format(choiceEvents.get(choiceEventSelector).getGraphic(),player.getCharacter(),setWidth(player.getFood(), 2),setWidth(player.getHeight(), 5),setWidth(player.getDay(), 2)));
                    choiceEvents.get(choiceEventSelector).setStatus(false);
                    choiceFalseCount += 1;
                    noEvent = true;
                }
                else {
                    EventSelector();
                }
            }
            case 2 -> {
                if (forcedBattleEvents.get(0).isStatus()) {
                    EventSelector();
                }
                else {
                   int battleEventSelector = random.nextInt(randomBattleEvents.size());
                   if (randomBattleEvents.get(battleEventSelector).isStatus()) {
                       currentBattleEvent = randomBattleEvents.get(battleEventSelector);
                       graphicArea.setText(String.format(currentBattleEvent.getGraphic(),player.getCharacter(),setWidth(player.getFood(), 2),setWidth(player.getHeight(), 5),setWidth(player.getDay(), 2)));
                       EventActionPanelSelector(7);
                       noEvent = true;
                   }
                   else {
                        EventSelector();
                   }
                }
            }
            case 3 -> {
                noEvent = false;
                int nothingSelector = random.nextInt(3);
                switch(nothingSelector) {
                    case 0 -> {
                        nothingText = """
                                                                                                      |
                                      
                                                         \\_
                                                           \\_   %s           
                                                             \\_ /|\\  ____      HP: %s
                                                               \\/_\\_/___/_   Mana: %s
                                          
                                        %s
                                               Food: %s          Height: %s          Day: %s       
                                      """;
                        graphicArea.setText(String.format(nothingText, player.getCharacter(), setWidth(player.getHP(),3), setWidth(player.getMana(),3), """
                                                                                                                                Nothing of note happens today. You rest to prepare to climb
                                                                                                                                  tomorrow. Eat another food ration to regain HP/Mana?
                                                                                                                                """,setWidth(player.getFood(), 2),setWidth(player.getHeight(), 5),setWidth(player.getDay(), 2)));
                        
                    }
                    case 1 -> {
                        nothingText = """
                                                                                                      |
                                      
                                                         \\_
                                                           \\_     \\%s/           
                                                             \\_     |          HP: %s
                                                               \\___<_\\____   Mana: %s
                                          
                                        %s
                                               Food: %s          Height: %s          Day: %s       
                                      """;
                        graphicArea.setText(String.format(nothingText, player.getCharacter(), setWidth(player.getHP(),3), setWidth(player.getMana(),3), """
                                                                                                                                Nothing of note happens today. You stretch out and prepare
                                                                                                                                  for tomorrow. Eat another food ration to regain HP/Mana?
                                                                                                                                """,setWidth(player.getFood(), 2),setWidth(player.getHeight(), 5),setWidth(player.getDay(), 2)));
                    }
                    case 2 -> {
                        nothingText = """
                                                                                                      |
                                      
                                                         \\_
                                                           \\_         %s
                                                             \\_       /|\\      HP: %s
                                                               \\______/_\\_   Mana: %s
                                          
                                        %s
                                               Food: %s          Height: %s          Day: %s       
                                      """;
                        graphicArea.setText(String.format(nothingText, player.getCharacter(), setWidth(player.getHP(),3), setWidth(player.getMana(),3), """
                                                                                                                                Nothing of note happens today. You admire your progress and
                                                                                                                                  prepare. Eat another food ration to regain HP/Mana?
                                                                                                                                """,setWidth(player.getFood(), 2),setWidth(player.getHeight(), 5),setWidth(player.getDay(), 2)));
                    }
                }

                EventActionPanelSelector(0);
            }
        }                    
    }
    
    //text for trying to use spell w/o enough mana
    private void NotEnoughMana() {
        TimerTask notEnoughMana = new TimerTask() {
            @Override
            public void run() {
                EventActionPanelSelector(10);
            }
        };
        battleLabel.setText("Not enough mana!");
        timer.schedule(notEnoughMana, 1500);
    }
    
    //text for dragon battle
    private void YouCantRun() {
        TimerTask youCantRun = new TimerTask() {
            @Override
            public void run() {
                EventActionPanelSelector(9);
            }
        };
        battleLabel.setText("You can't run!");
        timer.schedule(youCantRun, 1500);
    }
    
    //creates battle events (needed created later due to difficulty variations
    private void CreateBattleEvents() {
        BattleEvent swordsman = new BattleEvent("""
                                                                                                                |
                                                
                                                
                                                           0                                      %s  
                                                         \\/|\\/                                    /|\\  
                                                          / \\                                     / \\
                                                
                                                  During your climb you encounter a swordsman! He offers you a
                                                  sword and challenges you to a duel!
                                                
                                                         Food: %s          Height: %s          Day: %s
                                                """, true, """
                                                                                            
                                                           
                                                           
                                                                      0
                                                                     /|\\/
                                                                     / \\
                                                           
                                                           
                                                                    HP: %s
                                                           
                                                           
                                                            """, """
                                                                
                                                                
                                                                
                                                                           0%s
                                                                          /|%s%s
                                                                          / \\
                                                                
                                                                
                                                                         HP: %s
                                                                
                                                                
                                                                 """, "Swordsman", 80, player.getDifficulty(), 20, " /\\", 20, "Slash","\\_", 25, "Jab", "\\/)", 30, "Slice", 30, 25, 20);
        forcedBattleEvents.add(swordsman);
        
        BattleEvent jerry = new BattleEvent("""
                                                                                                                |
                                                            O 0 o
                                                             \\|/
                                                           O /                                    %s  
                                                          /|                                      /|\\  
                                                          / \\                                     / \\
                                                
                                                  During your climb you encounter Jerry the Balloon Man! He
                                                  offers you the secret to his abilities if you defeat him!
                                                
                                                         Food: %s          Height: %s          Day: %s
                                                """, true, """
                                                                                            
                                                                       O 0 o
                                                                        \\|/
                                                                      O /
                                                                     /| 
                                                                     / \\
                                                           
                                                           
                                                                    HP: %s
                                                           
                                                           
                                                            """, """
                                                                 %s%s%s
                                                                         HP: %s
                                                                                                                            
                                                                                                                            
                                                                 """, "Jerry the Balloon Man", 100, player.getDifficulty(), 25, """
                                                                                                                                                              * * *  
                                                                                                                                                               \\|/
                                                                                                                                                              \\ O /
                                                                                                                                                                |
                                                                                                                                                               / \\
                                                                                                                                     
                                                           
                                                                                                                                     """, 35, "Balloon Drop", """
                                                                                                                                                                                              
                                                                                                                                                              
                                                                                                                                                                                 )      0
                                                                                                                                                                        O _     )    O /
                                                                                                                                                                       /|         ) /     o
                                                                                                                                                                       / \\              /
                                                           
                                                                                                                                                              """, 15, "Wind", """
                                                                                                                                                                                                                
                                                                                                                                                                                                    *
                                                                                                                                                                                               *    |    *
                                                                                                                                                                                         O     |      *  |
                                                                                                                                                                                        /|\\      *    |    *
                                                                                                                                                                                        / \\      |         |
                                                           
                                                                                                                                                                               """, 10, "Balloon Pop", 25, 20, 30);
        forcedBattleEvents.add(jerry);
        
        BattleEvent robot = new BattleEvent("""
                                                                                                                |
                                                        ___i_  
                                                       |  O o|  
                                                       |___W_|                                    %s  
                                                        |\\c |\\c                                   /|\\  
                                                        |___|                                     / \\
                                                         Y Y
                                                  During your climb you encounter the robot! It boots up and
                                                  assumes a threatening stance!
                                                
                                                         Food: %s          Height: %s          Day: %s
                                                """, true,      """
                                                                                            
                                                                    ___i_
                                                                   |  O o|
                                                                   |___W_|
                                                                    |\\c |\\c
                                                                    |___|
                                                                     Y Y
                                                           
                                                                    HP: %s
                                                           
                                                           
                                                            """, """
                                                                
                                                                         ___i_%s%s%s
                                                                        |  O o|
                                                                        |___W_|
                                                                         |\\c |\\c
                                                                         |___|
                                                                          Y Y
                                                                
                                                                         HP: %s
                                                                
                                                                
                                                                 """, "Robot", 120, player.getDifficulty(), 30, "~~~~~~~~~~~~~~~~~~~", 25, "Wave Beam","\\/\\/\\/\\/\\/\\/\\/\\/\\/\\", 35, "Bolt", " ) ) ) ) ) ) ) ) )", 15, "Supersonic Blast", 20, 30, 25);
        forcedBattleEvents.add(robot);
        
        BattleEvent dragon = new BattleEvent("""
                                                           ___                                    o 0 O      |
                                             ___         _/  ò\\______                              \\|/  
                                                \\_    __/   _________\\                           O /   
                                                  \\__/_    / VVVVVVVV                           /|    _i___
                                                   \\      |                                %s  / \\  |O o  |
                                             \\_            \\_ɅɅɅɅɅɅɅɅ_                     /|\\       |_W___|
                                                ___    ______________/                     / \\      ɔ/| ɔ/|
                                               /  /   /                                          0    |___|
                                             _/   \\   \\__                                      \\/|\\    Y Y
                                                   \\_____K                                      / \\
                                             
                                             """, true, """
                                                                      ___               
                                                        ___         _/  ò\\______
                                                           \\_    __/   _________\\
                                                             \\__/_    / VVVVVVVV  
                                                              \\      |  
                                                        \\_            \\_ɅɅɅɅɅɅɅɅ_ 
                                                           ___    ______________/ 
                                                          /  /   /
                                                        _/   \\   \\__      
                                                              \\_____K   HP: %s
                                                        
                                                        """, """
                                                                           ___               
                                                             ___         _/  ò\\______
                                                                \\_    __/   _________\\
                                                                  \\__/_    / VVVVVVVV 
                                                                   \\      | %s%s%s
                                                             \\_            \\_ɅɅɅɅɅɅɅɅ_ 
                                                                ___    ______________/
                                                               /  /   /
                                                             _/   \\   \\__ 
                                                                   \\_____K   HP: %s
                                                             
                                                             """, "Dragon", 500, player.getDifficulty(), 0, " <O   <O   <O", 40, "Triple Fireball", "RRROOOAAARRRR!!", 25, "Fearsome Roar", "/\\/\\/\\/\\/\\/\\/\\/\\", 30, "Dragon Bolt", 25,20,30);
        forcedBattleEvents.add(dragon);
        
        BattleEvent luchador = new BattleEvent("""
                                                                                                               |
                                                
                                                
                                                          @                                      %s  
                                                         /|\\                                     /|\\  
                                                         / \\                                     / \\
                                                
                                                 During your climb you encounter a luchador wrestler, who
                                                 challenges you to a fight!
                                                
                                                        Food: %s          Height: %s          Day: %s
                                               """, true,   """
                                                                                            
                                                           
                                                           
                                                                      @
                                                                     /|\\
                                                                     / \\
                                                           
                                                           
                                                                    HP: %s
                                                           
                                                           
                                                            """, """
                                                                 %s%s%s
                                                                         HP: %s
                                                                                                                            
                                                                                                                            
                                                                 """, "Luchador", 75, player.getDifficulty(), 15, """
                                                                                                                    
                                                                                                                  
                                                                                                                                        -  @_
                                                                                                                                       --  |\\_
                                                                                                                                        -    \\
                                                                                                                                     
                                                           
                                                                                                                  """, 25, "Flying Kick", """
                                                                                                                    
                                                                                                                                          
                                                                                                                                                                    
                                                                                                                                                                  _\\_@_
                                                                                                                                                                     \\
                                                                                                                                     
                                                           
                                                                                                                                          """, 15, "Dive", """
                                                                                                                    
                                                                                                                                          
                                                                                                                                                                                 \\@/
                                                                                                                                                                                   \\/\\
                                                                                                                                                                                      
                                                                                                                                     
                                                           
                                                                                                                                                           """, 20, "Senton Bomb", 25, 20, 30);
        randomBattleEvents.add(luchador);
        
        BattleEvent jester = new BattleEvent("""
                                                                                                             |
                                                
                                                                                            
                                                       'O'o                                    %s  
                                                       /|\\|                                    /|\\  
                                                       / \\                                     / \\
                                                                                             
                                               During your climb you encounter a jester, who offers a battle
                                               (of wits)!
                                                                                             
                                                      Food: %s          Height: %s          Day: %s
                                             """, true, """
                                                                                            
                                                           
                                                           
                                                                  'O'o
                                                                  /|\\|
                                                                  / \\
                                                           
                                                           
                                                                 HP: %s
                                                           
                                                           
                                                        """, """
                                                             %s%s%s
                                                                      HP: %s
                                                                                                                            
                                                                                                                            
                                                             """, "Jester", 100, player.getDifficulty(), 25, """
                                                                                                                                                 
                                                                                                                        .
                                                                                                                      .   .o
                                                                                                                      \\'O'/|
                                                                                                                        |
                                                                                                                       / \\
                                                                                                             
                                                                                                             """, 10, "Juggle", """
                                                                                                                                                                    
                                                                                                                                                                    
                                                                                                                                
                                                                                                                                          'O'o  "What's the
                                                                                                                                          /|\\|  deal with 
                                                                                                                                          / \\   airline food?"
                                                                                                                                
                                                                                                                                """, 5, "Joke", """
                                                                                                                                                                                    
                                                                                                                                                
                                                                                                                                                
                                                                                                                                                          'O' o
                                                                                                                                                          /|\\/
                                                                                                                                                          / \\
                                                                                                                                                
                                                                                                                                                """, 15, "Staff Wave", 30, 25, 20);
        randomBattleEvents.add(jester);
        
        BattleEvent yeti = new BattleEvent("""
                                                     ___                                                   |
                                                   </ òó\\>
                                                    \\__w/
                                                   //| |\\\\                                    %s  
                                                   W |_| W                                    /|\\  
                                                    // \\\\                                     / \\
                                                    Q   Q
                                             During your climb you encounter a yeti! It roars and prepares
                                             to attack!
                                           
                                                    Food: %s          Height: %s          Day: %s
                                           """, false, """
                                                                 ___                       
                                                               </ òó\\>
                                                                \\__w/
                                                               //| |\\\\
                                                               W |_| W
                                                                // \\\\
                                                                Q   Q
                                                       
                                                                HP: %s
                                                       
                                                       
                                                       """, """
                                                            %s%s%s
                                                                     HP: %s
                                                            
                                                            
                                                            """, "Yeti", 225, player.getDifficulty(), 25, """
                                                                                                                    ___                       
                                                                                                                  </ òó\\>
                                                                                                                   \\__0/  "ROAR!!"
                                                                                                                  //| |\\\\
                                                                                                                  W |_| W
                                                                                                                   // \\\\
                                                                                                                   Q   Q
                                                                                                          """, 15, "Roar", """
                                                                                                                                     ___                       
                                                                                                                                  M</ òó\\>M
                                                                                                                                  \\\\\\__w///
                                                                                                                                   \\| |//
                                                                                                                                     |_|
                                                                                                                                    // \\\\
                                                                                                                                    Q   Q
                                                                                                                           """, 30, "Scratch", """
                                                                                                                                                                ___                
                                                                                                                                                              </ òó\\> 
                                                                                                                                                        --    _\\__w/
                                                                                                                                                     -----   /// /\\\\
                                                                                                                                                        --   W/_/  W
                                                                                                                                                             // \\\\
                                                                                                                                                             Q   Q
                                                                                                                                               """, 40, "Charge", 25, 20, 30);
        randomBattleEvents.add(yeti);
        
        BattleEvent goblins = new BattleEvent("""
                                                                                                              |
                                              
                                                            'o'
                                                     'o'    '⅄'                                  %s  
                                                     '⅄' 'o'                                     /|\\  
                                                         '⅄'                                     / \\
                                              
                                                During your climb you encounter a horde of goblins, and they
                                                prepare to attack!
                                              
                                                       Food: %s          Height: %s          Day: %s
                                              """, true, """
                                                   
                                                   
                                                                 'o'
                                                          'o'    '⅄'
                                                          '⅄' 'o' 
                                                              '⅄' 
                                                         
                                                   
                                                            HP: %s
                                                   
                                                   
                                                   """, """
                                                        %s%s%s
                                                                 HP: %s
                                                        
                                                        
                                                        """, "Goblin", 60, player.getDifficulty(), 15, """
                                                                                                                                           
                                                                                                       
                                                                                                                           ,'o',
                                                                                                             ,'o',           ⅄ 
                                                                                                               ⅄ ,'o',
                                                                                                                   ⅄  
                                                                                                       
                                                                                                       """, 15, "Mischief", """
                                                                                                                                                                
                                                                                                                            
                                                                                                                                         ,'o',
                                                                                                                                  ,'o',    ⅄ 
                                                                                                                                    ⅄           ,'o',
                                                                                                                                                  ⅄  
                                                                                                                            
                                                                                                                            """, 15, "Tomfoolery", """
                                                                                                                                                                                       
                                                                                                                                                   
                                                                                                                                                                ,'o',
                                                                                                                                                                  ⅄    ,'o',
                                                                                                                                                             ,'o',       ⅄ 
                                                                                                                                                               ⅄  
                                                                                                                                                   
                                                                                                                                                   """, 15, "Shenanigans", 25, 30, 20);
        randomBattleEvents.add(goblins);
        
        BattleEvent wizard = new BattleEvent("""
                                                                                                             |
                                                
                                                        Ʌ                                   
                                                        0                                      %s  
                                                       /|\\                                     /|\\  
                                                       / \\                                     / \\
                                                                                             
                                               During your climb you encounter a wizard, who offers to teach
                                               you a spell if you best him in combat!
                                                                                             
                                                      Food: %s          Height: %s          Day: %s
                                             """, true, """
                                                                                            
                                                           
                                                                   Ʌ
                                                                   0  
                                                                  /|\\ 
                                                                  / \\
                                                           
                                                           
                                                                 HP: %s
                                                           
                                                           
                                                        """, """
                                                                                                 
                                                             
                                                                        Ʌ
                                                                        0 _%s%s%s
                                                                       /| 
                                                                       / \\
                                                             
                                                             
                                                                      HP: %s
                                                             
                                                             
                                                             """, "Wizard", 80, player.getDifficulty(), 20, "          <O", 20, "Fireball", " o 0 O 0 o O", 15, "Bubble", "/\\/\\/\\/\\/\\/\\", 25, "Shock", 25, 30, 20);
        randomBattleEvents.add(wizard);
        
        BattleEvent ghost = new BattleEvent("""
                                                                                                                |
                                                                
                                                           O   
                                                          /|\\                                     %s  
                                                           V                                      /|\\  
                                                                                                  / \\
                                                
                                                  During your climb you encounter the ghost of another climber!
                                                  You draw your sword out of fear!
                                                
                                                         Food: %s          Height: %s          Day: %s
                                                """, true,  """
                                                                                            
                                                                      
                                                                       O
                                                                      /|\\
                                                                       V 
                                                                     
                                                           
                                                           
                                                                     HP: %s
                                                           
                                                           
                                                            """, """
                                                                 %s%s%s
                                                                          HP: %s
                                                                                                                            
                                                                                                                            
                                                                 """, "Ghost", 50, player.getDifficulty(), 10, """
                                                                                                                                               
                                                                                                                                      _
                                                                                                                          O _   ,    / \\    .
                                                                                                                         /|          \\_/ 
                                                                                                                          V       o       *
                                                                                                               
                                                                                                               
                                                                                                               """, 25, "Poltergeist", """
                                                                                                                                                                       
                                                                                                                                       
                                                                                                                                                           O _ "BOO!"
                                                                                                                                                           |\\    
                                                                                                                                                           V
                                                                                                                                       
                                                                                                                                       
                                                                                                                                       """, 15, "Scare", """
                                                                                                                                                         
                                                                                                                                                         
                                                                                                                                                         
                                                                                                                                                         
                                                                                                                                                         
                                                                                                                                                         
                                                                                                                                                         
                                                                                                                                                         """, 0, "Vanish", 0, 0, 0);
        randomBattleEvents.add(ghost);
        
        BattleEvent gnome = new BattleEvent("""
                                                                                                                |
                                                                
                                                               
                                                           Ʌ                                      %s  
                                                           0                                      /|\\  
                                                          '⅄'                                     / \\
                                                
                                                  During your climb you encounter a gnome! You mistrust his
                                                  joy and whimsy and draw your sword!
                                                
                                                         Food: %s          Height: %s          Day: %s
                                                """, true, """
                                                                                            
                                                                      
                                                                       
                                                                       Ʌ
                                                                       0 
                                                                      '⅄'

                                                           
                                                                     HP: %s
                                                           
                                                           
                                                            """, """
                                                                                                  
                                                                 
                                                                 
                                                                            Ʌ
                                                                            0, %s%s%s
                                                                           '⅄
                                                                 
                                                                 
                                                                          HP: %s
                                                                                                                            
                                                                                                                            
                                                                 """, "Gnome", 50, player.getDifficulty(), 0, "* * *", 10, "Pollen Puff", "~~~~~~~", 5, "Sweet Aroma", "+", -10, "Heal", 30, 35, 25);
        randomBattleEvents.add(gnome);
    }
    
    //handled when you win a battle
    private void DefeatedEnemy() {
        graphicArea.setText("""











                            """);
        battleLabel.setText("You defeated " + currentBattleEvent.getName() + "!");
        if ("Swordsman".equals(currentBattleEvent.getName())) {
            swordsmanDefeated = true;
        }
        if ("Jerry the Balloon Man".equals(currentBattleEvent.getName())) {
            jerryDefeated = true;
        }
        if ("Robot".equals(currentBattleEvent.getName())) {
            robotDefeated = true;
        }
        if ("Dragon".equals(currentBattleEvent.getName())) {
            dragonDefeated = true;
        }
        currentBattleEvent.setStatus(false);
        CallBattleRewards();
    }
    
    //resets graphic and calls enemy attack, also handles ally attacks for dragon battle
    private void AttackReset() {
        TimerTask attackReset = new TimerTask() {
            @Override
            public void run() {
                if ("Dragon".equals(currentBattleEvent.getName())) {
                    graphicArea.setText(currentBattleEvent.getStaticBattleGraphic());
                    if (currentBattleEvent.getHP() == 0) {
                        DefeatedEnemy();
                    }
                    else {
                        TimerTask dragonAttack = new TimerTask() {
                            @Override
                            public void run() {
                                graphicArea.setText(currentBattleEvent.getStaticBattleGraphic());
                                battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", "               /|    ", "  ", "  ", player.getCharacter(), "  ", "  ", "\\/", "  ","  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                                OpponentAttack();
                            }
                        };
                        TimerTask robot = new TimerTask() {
                            @Override
                            public void run() {
                                graphicArea.setText(currentBattleEvent.getStaticBattleGraphic());
                                int attack = random.nextInt(3);
                                switch (attack) {
                                    case 0 -> {
                                        battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", " ( ( ( ( ( ( ( /|( ( ", "  ", "  ", player.getCharacter(), "  ", "  ", "\\/", "  ","  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));                                    currentBattleEvent.reduceHP(25);
                                        currentBattleEvent.reduceHP(25);
                                        battleLabel.setText("Robot used Supersonic Blast!");
                                    }
                                    case 1 -> {
                                        battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", "~~~~~~~~~~~~~~~/|~~~~", "  ", "  ", player.getCharacter(), "  ", "  ", "\\/", "  ","  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                                        currentBattleEvent.reduceHP(30);
                                        battleLabel.setText("Jerry used Wave Beam!");
                                    }
                                    case 2 -> {
                                        battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", "\\/\\/\\/\\/\\/\\/\\/\\/|/\\/\\", "  ", "  ", player.getCharacter(), "  ", "  ", "\\/", "  ","  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                                        currentBattleEvent.reduceHP(35);
                                        battleLabel.setText("Robot used Bolt!");
                                    }
                                }
                                if (currentBattleEvent.getHP() == 0) {
                                    DefeatedEnemy();
                                }
                                else {
                                    timer.schedule(dragonAttack, 1000);
                                }
                            }
                        };
                        TimerTask jerry = new TimerTask() {
                            @Override
                            public void run() {
                                graphicArea.setText(currentBattleEvent.getStaticBattleGraphic());
                                int jerryAttack = random.nextInt(3);
                                switch (jerryAttack) {
                                    case 0 -> {
                                        battleArea.setText(String.format(battleGraphic, "* ", "o 0 O", "|*", "*|", "  ", "|              /|    ", "  ", "  ", player.getCharacter(), "  ", "  ", "\\/", "  ","  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));                                    currentBattleEvent.reduceHP(25);
                                        currentBattleEvent.reduceHP(15);
                                        battleLabel.setText("Jerry used Balloon Pop!");
                                    }
                                    case 1 -> {
                                        battleArea.setText(String.format(battleGraphic, " (", "o 0 O", "( ", "  ", "_ ", " (              |    ", "  ", "  ", player.getCharacter(), "  ", "  ", "\\/", "  ","  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                                        currentBattleEvent.reduceHP(25);
                                        battleLabel.setText("Jerry used Wind!");
                                    }
                                    case 2 -> {
                                        battleArea.setText(String.format(battleGraphic, "  ", "* * *", "  ", "  ", "\\ ", "               /|    ", "  ", "  ", player.getCharacter(), "  ", "  ", "\\/", "  ","  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                                        currentBattleEvent.reduceHP(35);
                                        battleLabel.setText("Jerry used Balloon Drop!");
                                    }
                                }
                                if (currentBattleEvent.getHP() == 0) {
                                    DefeatedEnemy();
                                }
                                else {
                                    timer.schedule(robot, 1000);
                                }
                            }
                        };
                        int swordsmanAttack = random.nextInt(3);
                        switch (swordsmanAttack) {
                            case 0 -> {
                                battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", "               /|    ", "  ", "  ", player.getCharacter(), "  ", "  ", "\\/", "  ","  ", "  ", "  ", setWidth(player.getHP(),3), " (", "\\/", setWidth(player.getMana(),3)));
                                currentBattleEvent.reduceHP(25);
                                battleLabel.setText("Swordsman used Slice!");
                            }
                            case 1 -> {
                                battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", "               /|    ", "  ", "  ", player.getCharacter(), "  ", "  ", "\\/", "  ","  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "_/", setWidth(player.getMana(),3)));
                                currentBattleEvent.reduceHP(20);
                                battleLabel.setText("Swordsman used Jab!");
                            }
                            case 2 -> {
                                battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", "               /|    ", "  ", "  ", player.getCharacter(), "  ", "  ", "\\/", "  ","  ", "/\\", "  ", setWidth(player.getHP(),3), "  ", "  ", setWidth(player.getMana(),3)));
                                currentBattleEvent.reduceHP(30);
                                battleLabel.setText("Swordsman used Slash!");
                            }
                        }
                        if (currentBattleEvent.getHP() == 0) {
                            DefeatedEnemy();
                        }
                        else {
                            timer.schedule(jerry, 1000);
                        }
                    }
                }
                else {
                    battleArea.setText(String.format(battleGraphic, "  ", "  ", "  ", "  ", "  ", "  ", player.getCharacter(), "  ","  ", "\\/", "  ", "  ", "  ", setWidth(player.getHP(),3), setWidth(player.getMana(),3)));
                    graphicArea.setText(currentBattleEvent.getStaticBattleGraphic());
                    if (currentBattleEvent.getHP() == 0) {
                        DefeatedEnemy();
                    }
                    else {
                        OpponentAttack();
                    }
                }
            }
        };
        timer.schedule(attackReset, 1000);
    }
    
    private void CallBattleRewards() {
        TimerTask callBattleRewards = new TimerTask() {
            @Override
            public void run() {
                if ("Dragon".equals(currentBattleEvent.getName())) {
                    actionPanel.removeAll();
                    actionPanel.add(endGameButton);
                }
                else {
                    BattleRewards(currentBattleEvent.getName());
                }
            }
        };
        timer.schedule(callBattleRewards, 1000);
    }
    
    //handles enemy attacks
    private void OpponentAttack() {
        TimerTask attackReset = new TimerTask() {
            @Override
            public void run() {
                int attackSelector = random.nextInt(3);
                TimerTask battleGraphicReset = new TimerTask() {
                    @Override
                    public void run() {
                        if (player.getHP() <= 0) {
                            if (player.getDay() == swordsmanBattleDay) {
                                choiceEvents.get(5).setStatus(true);
                                battleArea.setText(String.format(battleGraphic, "  ", "  ", "  ", "  ", "  ", "  ", player.getCharacter(), "  ","  ", "\\/", "  ", "  ", "  ", setWidth(player.getHP(),3), setWidth(player.getMana(),3)));
                                graphicArea.setText(currentBattleEvent.getStaticBattleGraphic());
                                TimerTask swordsmanDefeatText = new TimerTask() {
                                    @Override
                                    public void run() {
                                        battleLabel.setText("He lets you keep the sword for protection.");
                                        player.reduceHP(-25);
                                        EndBattle();
                                    }
                                };
                                battleLabel.setText("You are defeated by " + currentBattleEvent.getName() + ".");
                                timer.schedule(swordsmanDefeatText, 1500);
                            }
                            else {
                                if (("Jerry the Balloon Man".equals(currentBattleEvent.getName())) || ("Robot".equals(currentBattleEvent.getName()))) {
                                    currentBattleEvent.setStatus(false);
                                }
                                battleArea.setText("""
                                                                                   |










                                                   """);
                                TimerTask defeatText = new TimerTask() {
                                    @Override
                                    public void run() {
                                        if ("Dragon".equals(currentBattleEvent.getName())){
                                            actionPanel.removeAll();
                                            actionPanel.add(endGameButton);
                                        }
                                        else {
                                            battleLabel.setText("You retreat 300ft and eat a food ration to recover HP/Mana.");
                                            player.reduceFood(1);
                                            player.reduceHeight(-300);
                                            player.reduceHP(-25);
                                            player.reduceMana(-15);
                                            EndBattle();
                                        }
                                    }
                                };
                                battleLabel.setText("You are defeated by " + currentBattleEvent.getName() + ".");
                                timer.schedule(defeatText, 1500);
                            }
                        }
                        else {
                            if ("Dragon".equals(currentBattleEvent.getName())) {
                                battleArea.setText(String.format(battleGraphic, "  ", "o 0 O", "  ", "  ", "  ", "               /|    ", "  ", "  ", player.getCharacter(), "  ", "  ", "\\/", "  ","  ", "  ", "  ", setWidth(player.getHP(),3), "  ", "\\/", setWidth(player.getMana(),3)));
                            }
                            else {
                                battleArea.setText(String.format(battleGraphic, "  ", "  ", "  ", "  ", "  ", "  ", player.getCharacter(), "  ","  ", "\\/", "  ", "  ", "  ", setWidth(player.getHP(),3), setWidth(player.getMana(),3)));
                            }
                            graphicArea.setText(currentBattleEvent.getStaticBattleGraphic());
                            if (player.getDay() == swordsmanBattleDay) {
                                EventActionPanelSelector(8);  
                            }
                            else {
                                EventActionPanelSelector(9);
                            }
                        }
                    }
                };
                switch (attackSelector) {
                    case 0 -> {
                        graphicArea.setText(currentBattleEvent.UseAttack1());
                        player.reduceHP(currentBattleEvent.getAttack1Damage());
                        battleLabel.setText(currentBattleEvent.getName() + " used " + currentBattleEvent.getAttack1Name() + "!");
                        timer.schedule(battleGraphicReset, 1000);
                    }
                    case 1 -> {
                        graphicArea.setText(currentBattleEvent.UseAttack2());
                        player.reduceHP(currentBattleEvent.getAttack2Damage());
                        battleLabel.setText(currentBattleEvent.getName() + " used " + currentBattleEvent.getAttack2Name() + "!");
                        timer.schedule(battleGraphicReset, 1000);
                    }
                    case 2 -> {
                        graphicArea.setText(currentBattleEvent.UseAttack3());
                        player.reduceHP(currentBattleEvent.getAttack3Damage());
                        battleLabel.setText(currentBattleEvent.getName() + " used " + currentBattleEvent.getAttack3Name() + "!");
                        timer.schedule(battleGraphicReset, 1000);
                    }
                }
            }
        };
        timer.schedule(attackReset, 1000);
    }
    
    //handles a battle ending
    private void EndBattle() {
        TimerTask endBattle = new TimerTask() {
            @Override
            public void run() {
                if ("Dragon".equals(currentBattleEvent.getName())){
                    actionPanel.removeAll();
                    actionPanel.add(endGameButton);
                }
                else {
                    EventActionPanelSelector(1);
                }
            }
        };
        timer.schedule(endBattle, 2000);
    }
    
    //handles rewards for each battle
    private void BattleRewards(String name) {
        switch (name) {
            case "Swordsman" -> {
                battleLabel.setText("You are gifted the Sword for winning!");
                choiceEvents.get(5).setStatus(true);
                EndBattle();
            }
            case "Jerry the Balloon Man" -> {
                battleLabel.setText("You are gifted the Air Tome for winning!");
                spells.add(windButton);
                choiceEvents.get(6).setStatus(true);
            }
            case "Robot" -> {
                battleLabel.setText("You are gifted the Thunder Tome for winning!");
                spells.add(boltButton);
                randomBattleEvents.get(2).setStatus(true);
            }
            case "Luchador" -> {
                battleLabel.setText("You gain 1 food ration for winning!");
                player.reduceFood(-1);
            }
            case "Jester" -> {
                battleLabel.setText("You climb an extra 200ft after winning!");
                player.reduceHeight(200);
            }
            case "Yeti" -> {
                battleLabel.setText("You gain 4 food rations for winning!");
                player.reduceFood(-4);
            }
            case "Goblin" -> {
                battleLabel.setText("You climb an extra 150ft after winning!");
                player.reduceHeight(150);
            }
            case "Wizard" -> {
                battleLabel.setText("You are gifted the Fire Tome for winning!");
                spells.add(fireballButton);
            }
            case "Ghost" -> {
                battleLabel.setText("You are gifted the Spirit Tome for winning!");
                spells.add(phantomButton);
            }
            case "Gnome" -> {
                battleLabel.setText("You are gifted the Curing Tome for winning!");
                spells.add(healButton);
            }
        }
        EndBattle();
    }

    //sets each action panel that need/could be called more than once
    private void EventActionPanelSelector(int panelNumber) {
        actionPanel.removeAll();
        switch (panelNumber) {
            case 0 -> {
                choiceEventButtonLayout(eatButton);
            }
            case 1 -> {
                actionPanel.add(standardEventButton);
            }
            case 2 -> {
                actionPanel.add(leftButton);
                actionPanel.add(blankPanel1);
                actionPanel.add(rightButton);
            }
            case 3 -> {
                choiceEventButtonLayout(takeButton);
            }
            case 4 -> {
                choiceEventButtonLayout(retrieveButton);
            }
            case 5 -> {
                choiceEventButtonLayout(jumpButton);
            }
            case 6 -> {
                choiceEventButtonLayout(fishButton);
            }
            case 7 -> {
                actionPanel.add(battleContinueButton);
            }
            case 8 -> {
                actionPanel.add(fightButton);
            }
            case 9 -> {
                if (spells.isEmpty()) {
                    actionPanel.add(fightButton);
                    actionPanel.add(blankPanel1);
                    actionPanel.add(runButton);
                }
                else {
                    actionPanel.add(fightButton);
                    actionPanel.add(blankPanel1);
                    actionPanel.add(spellsButton);
                    actionPanel.add(blankPanel2);
                    actionPanel.add(runButton);
                }
            }
            case 10 -> {
                for (int i = 0; i < spells.size(); i++) {
                    actionPanel.add(spells.get(i));
                }
                actionPanel.add(blankPanel1);
                actionPanel.add(cancelButton);
            }
            case 11 -> {
                actionPanel.add(door1Button);
                actionPanel.add(blankPanel1);
                actionPanel.add(door2Button);
                actionPanel.add(blankPanel2);
                actionPanel.add(door3Button);
            }
            case 12 -> {
                actionPanel.add(iceButton);
                actionPanel.add(blankPanel1);
                actionPanel.add(rockButton);
            }
        }
        actionPanel.revalidate();
    }
    
    //shorts code for choice events that have the same button layout
    private void choiceEventButtonLayout(JButton button) {
        actionPanel.add(button);
        actionPanel.add(blankPanel1);
        actionPanel.add(standardEventButton);
    }
    
    //creats GUI
    public static void main(String[] args) {
        ClimberBornAgain climberBornAgain = new ClimberBornAgain();
        climberBornAgain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        climberBornAgain.setSize(856,482);
        climberBornAgain.setLocationRelativeTo(null);       
        climberBornAgain.setVisible(true);
    }
}