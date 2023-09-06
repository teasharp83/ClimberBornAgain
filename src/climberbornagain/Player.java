/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package climberbornagain;

/**
 *
 * @author Teagan
 */
public class Player {
    
    private String character;
    private String name;
    private int difficulty;
    private int food;
    private int height;
    private int day;
    private int HP;
    private int mana;
    private int score;
    
    public Player (String chrctr, String nm, int diff, int scr) {
        character = chrctr;
        name = nm;
        difficulty = diff;
        score = scr;
        food = 25;
        height = 12500;
        day = 0;
        HP = 100;
        mana = 100;
    }

    public String getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getFood() {
        return food;
    }

    public int getHeight() {
        return height;
    }

    public int getDay() {
        return day;
    }

    public int getHP() {
        return HP;
    }

    public int getMana() {
        return mana;
    }
    
    public int getScore() {
        return score;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public void reduceFood(int loss) {
        food -= loss;
    }
    
    public void reduceHeight(int distance) {
        height -= distance;
        if (height < 0) {
            height = 0;
        }
    }
    
    public void increaseDay() {
        day += 1;
    }
    
    public void reduceHP(int damage) {
        HP -= damage;
        if (HP < 0) {
            HP = 0;
        }
        if (HP > 100) {
            HP = 100;
        }
    }
    
    public void reduceMana(int cost) {
        mana -= cost;
        if (mana < 0) {
            mana = 0;
        }
        if (mana > 100) {
            mana = 100;
        }
    }
}
