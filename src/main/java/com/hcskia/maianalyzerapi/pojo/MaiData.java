package com.hcskia.maianalyzerapi.pojo;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class MaiData {
    public static class CompositeIdId implements Serializable {
        @Column(name = "Id")
        private String Id;

        @Column(name = "LevelIndex")
        private int LevelIndex;

        public String getId(){
            return this.Id;
        }
        public void setId(String Id){
            this.Id = Id;
        }

        public int getLevelIndex(){
            return this.LevelIndex;
        }
        public void setLevelIndex(int LevelIndex){
            this.LevelIndex = LevelIndex;
        }
    }

    @EmbeddedId
    private CompositeIdId compositeId;

    @Column(name = "Achievements")
    private double Achievements;
    public double getAchievements(){
        return this.Achievements;
    }
    public void setAchievements(double Achievements){
        this.Achievements = Achievements;
    }

    @Column(name = "Fc")
    private String Fc;
    public String getFc(){
        return this.Fc;
    }
    public void setFc(String Fc){
        this.Fc = Fc;
    }

    @Column(name = "Fs")
    private String Fs;
    public String getFs(){
        return this.Fs;
    }
    public void setFs(String Fs){
        this.Fs = Fs;
    }

    @Column(name = "Level")
    private String Level;
    public String getLevel(){
        return this.Level;
    }
    public void setLevel(String Level){
        this.Level = Level;
    }

    @Column(name = "Title")
    private String Title;
    public String getTitle(){
        return this.Level;
    }
    public void setTitle(String Title){
        this.Title = Title;
    }

    @Column(name = "Type")
    private String Type;
    public String getType(){
        return this.Type;
    }
    public void setType(String Type){
        this.Type = Type;
    }
}
