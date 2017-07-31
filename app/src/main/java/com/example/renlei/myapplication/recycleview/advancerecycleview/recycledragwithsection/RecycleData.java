package com.example.renlei.myapplication.recycleview.advancerecycleview.recycledragwithsection;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Time 2017/7/26.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class RecycleData {

    public class GroupData {
        public String name;
        public boolean isExpande;
        public int type;

        public GroupData(String name, boolean isExpande, int type) {
            this.name = name;
            this.isExpande = isExpande;
            this.type = type;
        }

        public GroupData() {
        }
    }

    public class ChildData {
        public String name;

        public ChildData(String name) {
            this.name = name;
        }
    }

    public List<Pair<GroupData, List<ChildData>>> getData() {
        List<Pair<GroupData, List<ChildData>>> data = new ArrayList<>();
        data.add(getPair("通用",0));
        for (int i = 0;i<5;i++){
            data.add(getPair("设备"+i,1));
        }

        data.add(getPair("可以选择的设备",0));

        for (int i = 5;i<15;i++){
            data.add(getPair("设备"+i,2));
        }
        return data;
    }
    private Pair<GroupData,List<ChildData>> getPair(String groupName,int groupType){
        if (groupType == 0){
            GroupData groupData = new GroupData(groupName,false,0);
            List<ChildData>childDatas = new ArrayList<>();
            return  new Pair<>(groupData,childDatas);
        }else {
            GroupData groupData = new GroupData(groupName,false,groupType);
            List<ChildData>childDatas = new ArrayList<>();
            for (int i = 0;i<3;i++){
                ChildData childData = new ChildData("操作"+i);
                childDatas.add(childData);
            }
            return  new Pair<>(groupData,childDatas);
        }
    }
}
