package net.nwatmb.equipmentsmanager;


/**
 * Created by NWATMB on 2015/8/23.
 */
public class EquipmentPKG {
    private static int pkgNum = 20;         //最大缓存个数
    private static int index = 0;
    private static String[] sID = new String[pkgNum];
    private static String[] sName = new String[pkgNum];
    private static String[] sPosition = new String[pkgNum];
    private static String[] sBrand = new String[pkgNum];
    private static String[] sType = new String[pkgNum];
    private static String[] sSN = new String[pkgNum];
    private static String[] sSystem = new String[pkgNum];
    private static String[] sTime_input = new String[pkgNum];
    private static String[] sTime_in_use = new String[pkgNum];
    private static String[] sTest_log = new String[pkgNum];
    private static String[] sOperator = new String[pkgNum];
    private static String[] sRemark = new String[pkgNum];


    public static void setEquipmentPKG(String uID, String uName, String uPosition, String uBrand,
                                String uType, String uSN, String uSystem, String uTime_input,
                                String uTime_in_use, String uTest_log, String uOperator,
                                String uRemark)
    {
        if (uID != null)
        {
            EquipmentPKG.sID[index] = uID;
            EquipmentPKG.sName[index] = uName;
            EquipmentPKG.sPosition[index] = uPosition;
            EquipmentPKG.sBrand[index] = uBrand;
            EquipmentPKG.sType[index] = uType;
            EquipmentPKG.sSN[index] = uSN;
            EquipmentPKG.sSystem[index] = uSystem;
            EquipmentPKG.sTime_input[index] = uTime_input;
            EquipmentPKG.sTime_in_use[index] = uTime_in_use;
            EquipmentPKG.sTest_log[index] = uTest_log;
            EquipmentPKG.sOperator[index] = uOperator;
            EquipmentPKG.sRemark[index] = uRemark;

            EquipmentPKG.index++;

        }

    }



    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        EquipmentPKG.index = index;
    }

    public static String[] getsID() {
        return sID;
    }

    public static String[] getsName() {
        return sName;
    }

    public static String[] getsPosition() {
        return sPosition;
    }

    public static String[] getsBrand() {
        return sBrand;
    }

    public static String[] getsType() {
        return sType;
    }

    public static String[] getsSN() {
        return sSN;
    }

    public static String[] getsSystem() {
        return sSystem;
    }

    public static String[] getsTime_input() {
        return sTime_input;
    }

    public static String[] getsTime_in_use() {
        return sTime_in_use;
    }

    public static String[] getsTest_log() {
        return sTest_log;
    }

    public static String[] getsOperator() {
        return sOperator;
    }

    public static String[] getsRemark() {
        return sRemark;
    }
}
