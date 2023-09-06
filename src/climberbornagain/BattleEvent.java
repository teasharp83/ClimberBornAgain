/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package climberbornagain;

/**
 *
 * @author Teagan
 */
public class BattleEvent extends Event {
    
    private String staticBattleGraphic;
    private String battleGraphic;
    private String name;
    private int HP;
    private String attack1;
    private int attack1Damage;
    private String attack1Name;
    private String attack2;
    private int attack2Damage;
    private String attack2Name;
    private String attack3;
    private int attack3Damage;
    private String attack3Name;
    private int sliceDamage;
    private int jabDamage;
    private int slashDamage;
    private int rewardsNumber;
    
    public BattleEvent(String grphc, boolean stts, String sbg, String bg, String nm, int health, int diff, int diffMult, String attk1, int a1d, String a1n, String attk2, int a2d, String a2n, String attk3, int a3d, String a3n, int dmg1, int dmg2, int dmg3) {
        super(grphc,stts);
        staticBattleGraphic = sbg;
        battleGraphic = bg;
        name = nm;
        HP = health + (diff*diffMult);
        attack1 = attk1;
        attack1Damage = a1d;
        attack1Name = a1n;
        attack2 = attk2;
        attack2Damage = a2d;
        attack2Name = a2n;
        attack3 = attk3;
        attack3Damage = a3d;
        attack3Name = a3n;
        sliceDamage = dmg1;
        jabDamage = dmg2;
        slashDamage = dmg3;
    }

    public String getStaticBattleGraphic() {
        return String.format(staticBattleGraphic, ClimberBornAgain.setWidth(HP,3));
    }

    public int getHP() {
        return HP;
    }
    
    public String getName() {
        return name;
    }
    
    public String UseAttack1() {
        return String.format(battleGraphic, attack1, "", "", ClimberBornAgain.setWidth(HP,3));
    }

    public int getAttack1Damage() {
        return attack1Damage;
    }

    public String getAttack1Name() {
        return attack1Name;
    }
    
    public String UseAttack2() {
        return String.format(battleGraphic, "", attack2, "", ClimberBornAgain.setWidth(HP,3));
    }

    public int getAttack2Damage() {
        return attack2Damage;
    }

    public String getAttack2Name() {
        return attack2Name;
    }
    
    public String UseAttack3() {
        return String.format(battleGraphic, "", "", attack3, ClimberBornAgain.setWidth(HP,3));
    }

    public int getAttack3Damage() {
        return attack3Damage;
    }

    public String getAttack3Name() {
        return attack3Name;
    }

    public int getSliceDamage() {
        return sliceDamage;
    }

    public int getJabDamage() {
        return jabDamage;
    }

    public int getSlashDamage() {
        return slashDamage;
    }
    
    public void reduceHP(int damage) {
        HP -= damage;
        if (HP < 0) {
            HP = 0;
        }
    }
}

