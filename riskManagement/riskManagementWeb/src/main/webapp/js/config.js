(function($){
    $.URL = {
        "power":{
            "add":"rs/power/add",
            "update":"rs/power/update",
            "delete":"rs/power/delete",
            "list":"rs/power/list"
        }  ,
        "address":{
            "getCityByProvince"  :"rs/address/getCityByProvince" ,
            "findIdByArea"  :"rs/address/findByArea/{province},{city},{area}",
            "getProvinceList":"rs/address/getProvinceList",
            "getAreaByProvinceAndCity":"rs/address/getAreaByProvinceAndCity"
        },
        "report":
        {
            "showCraneReport" :"rs/report/showCraneReport",
            "exportCraneReport":"rs/report/exportCraneReport"
        }  ,
        "craneinspectreport":
        {
            "getUnitaddressByArea" :"rs/craneinspectreport/getUnitaddressByArea",
            "getAreaInfoByUnitAddress":"rs/craneinspectreport/getAreaInfoByUnitAddress",
            "getCraneInspectReportInfoByAddressAndEquipment":"rs/craneinspectreport/getCraneInspectReportInfoByAddressAndEquipment",
            "getAreaInfo":"rs/craneinspectreport/getAreaInfo",
            "showRiskRank":"rs/craneinspectreport/showRiskRank",
            "showRiskRankByValueRange":"rs/craneinspectreport/showRiskRankByValueRange",
            "getOneUnitAddressInfo":"rs/craneinspectreport/getOneUnitAddressInfo",
            "list":"rs/craneinspectreport/list",
            "upload" :"rs/craneinspectreport/upload",
            "addRepeat":"rs/craneinspectreport/addRepeat",
            "delete":"rs/craneinspectreport/delete",
            "update":"rs/craneinspectreport/update",
            "getOneUnitAddressInfo":"rs/craneinspectreport/getOneUnitAddressInfo",
            "getCraneInspectReportInfoById":"rs/craneinspectreport/getCraneInspectReportInfoById",
            "getCraneInspectReportInfoFromCircle":"rs/craneinspectreport/getCraneInspectReportInfoFromCircle",
            "getEquipmentVarietyList":"rs/craneinspectreport/getEquipmentVarietyList",
            "getCraneInspectReportInfoFromCircle":"rs/craneinspectreport/getCraneInspectReportInfoFromCircle",
            "getProvinceAvgRiskValue":"rs/craneinspectreport/getProvinceAvgRiskValue",
            "getCityAvgRiskValueByProvince":"rs/craneinspectreport/getCityAvgRiskValueByProvince",
            "getAreaAvgRiskValueByProvinceAndCity":"rs/craneinspectreport/getAreaAvgRiskValueByProvinceAndCity",
            "fuzzyQuery":"rs/craneinspectreport/fuzzyQuery",
            "fuzzyQueryByUnitAddress":"rs/craneinspectreport/fuzzyQueryByUnitAddress",
            "fuzzyQueryByUserPoint":"rs/craneinspectreport/fuzzyQueryByUserPoint",
            "fuzzyQueryBySafeManager":"rs/craneinspectreport/fuzzyQueryBySafeManager",
            "fuzzyQueryByEquipmentVariety":"rs/craneinspectreport/fuzzyQueryByEquipmentVariety",
            "fuzzyQueryByManufactureUnit":"rs/craneinspectreport/fuzzyQueryByManufactureUnit",
            "getCityRiskRankFormRiskRange":"rs/craneinspectreport/getCityRiskRankFormRiskRange",
            "getAreaRiskRankFormRiskRange":"rs/craneinspectreport/getAreaRiskRankFormRiskRange"  ,

            "getCraneInfoByFuzzyUnitAddress": "rs/craneinspectreport/getCraneInfoByFuzzyUnitAddress" ,
            "getCraneInfoByFuzzyManufactureUnit" :"rs/craneinspectreport/getCraneInfoByFuzzyManufactureUnit",
            "getCraneInfoByFuzzyUsePoint":"rs/craneinspectreport/getCraneInfoByFuzzyUsePoint",
            "getCraneInfoByFuzzySafeManager":"rs/craneinspectreport/getCraneInfoByFuzzySafeManager",
            "getCraneInfoByFuzzyEquipmentVariety":"rs/craneinspectreport/getCraneInfoByFuzzyEquipmentVariety",
            "getCraneInfoByFuzzyQuery":'rs/craneinspectreport/getCraneInfoByFuzzyQuery',
            "getUseTimeList":'rs/craneinspectreport/getUseTimeList',
            "getCraneInfoByEquipmentVariety":'rs/craneinspectreport/getCraneInfoByEquipmentVariety',
            "getProvinceRiskRankFormRiskRange":'rs/craneinspectreport/getProvinceRiskRankFormRiskRange',
            "getCityInfoByProvinceEquipmentVariety":'rs/craneinspectreport/getCityInfoByProvinceEquipmentVariety',
            "getAreaInfoByProvinceEquipmentVariety":'rs/craneinspectreport/getAreaInfoByProvinceEquipmentVariety',
            "getCityInfoByProvinceUseTime":'rs/craneinspectreport/getCityInfoByProvinceUseTime',
            "getAreaInfoByProvinceUseTime":'rs/craneinspectreport/getAreaInfoByProvinceUseTime',
             "getCraneInfoByUseTime":'rs/craneinspectreport/getCraneInfoByUseTime',
            "getCraneInfoByEquipmentVarietyUseTime":'rs/craneinspectreport/getCraneInfoByEquipmentVarietyUseTime',
            "getCityInfoByProvinceEquipmentAndUseTime":'rs/craneinspectreport/getCityInfoByProvinceEquipmentAndUseTime',
            "getAreaInfoByEquipmentVarietyUseTime":'rs/craneinspectreport/getAreaInfoByEquipmentVarietyUseTime'
        },
        "authority":{
            "add":"rs/authority/add"
        },
        "user":{
            "add":"rs/user/add",
            "update":"rs/user/update",
            "delete":"rs/user/delete",
            "list":"rs/user/list",
            "getId":"rs/user/getIdByName"
        },
        "authority":{
             "add":"rs/authority/add",
             "update":"rs/authority/update",
             "delete":"rs/authority/delete",
            "list":"rs/authority/list"
        },
        "userAuthority":{
              "add":"rs/userAuthority/add"
        },
        "riskColor":{
           "list":"rs/riskColor/list",
            "setColor":"rs/riskColor/setColor",
            "getRiskValueList":"rs/riskColor/getRiskValueList",
            "setColor":"rs/riskColor/setColor"
        },
        "dataRule":{
            "add":"rs/dataRule/addDataRole",
            "list":"rs/dataRule/list",
            "listAddress":"rs/dataRule/listAddress",
            "listProvince":"rs/dataRule/listProvince",
            "delete":"rs/dataRule/delete",
            "update":"rs/dataRule/updateDataRole"
        },
        "dataRuleAddress":{
            "getProvinceAndColorWithDataRole":"rs/dataRuleAddress/getProvinceAndColorWithDataRole",
            "getCityAndColorWithDataRole":"rs/dataRuleAddress/getCityAndColorWithDataRole",
            "getAreaAndColorWithDataRole":"rs/dataRuleAddress/getAreaAndColorWithDataRole",
            "getAddressesIdBydRoleName":"rs/dataRuleAddress/getAddressIdBydRoleName",
            "getProvinceInfoByProvinceEquipmentVariety":"rs/dataRuleAddress/getProvinceInfoByProvinceEquipmentVariety",
            "getProvinceInfoByProvinceUseTime":"rs/dataRuleAddress/getProvinceInfoByProvinceUseTime",
            "getProvinceInfoByProvinceEquipmentVarietyAndUseTime":"rs/dataRuleAddress/getProvinceInfoByProvinceEquipmentVarietyAndUseTime"


        },
        "userDataRole":{
            "add":"rs/userDataRole/add",
            "update":"rs/userDataRole/update",
            "delete":"rs/userDataRole/delete",
            "list":"rs/userDataRole/list"
        }
    }
})(jQuery);