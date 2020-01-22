package com.github.malitsplus.shizurunotes.data;


import com.github.malitsplus.shizurunotes.data.action.Action;

import java.util.List;

public class Skill {
    public final static int UNION_BURST = 1;
    public final static int UNION_BURST_EVOLUTION = 2;
    public final static int MAIN = 3;
    public final static int MAIN_EVOLUTION = 4;
    public final static int SP = 5;
    public final static int EX = 6;
    public final static int EX_EVOLUTION = 7;

    public List<? extends Action> actionList;
    public int skillTag;

    public int skillId;
    public String name;
    public int skillType;
    public int skillAreaWidth;
    public double skillCastTime;
    public String description;
    public int iconType;

    public String iconUrl;


}
