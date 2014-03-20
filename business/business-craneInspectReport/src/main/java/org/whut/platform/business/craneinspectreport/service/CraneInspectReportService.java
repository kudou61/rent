package org.whut.platform.business.craneinspectreport.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.whut.platform.business.address.entity.Address;
import org.whut.platform.business.address.service.AddressService;
import org.whut.platform.business.craneinspectreport.entity.CraneInspectReport;
import org.whut.platform.business.craneinspectreport.mapper.CraneInspectReportMapper;
import org.whut.platform.fundamental.jxl.model.ExcelMap;
import org.whut.platform.fundamental.jxl.utils.JxlExportImportUtils;
import org.whut.platform.fundamental.mongo.connector.MongoConnector;
import org.whut.platform.fundamental.util.tool.ToolUtil;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: zhuzhenhua
 * Date: 14-3-17
 * Time: 下午10:39
 * To change this template use File | Settings | File Templates.
 */
public class CraneInspectReportService {
    @Autowired
    private CraneInspectReportMapper mapper;
    @Autowired
    private  AddressService addressService;
    private  ExcelMap excelMap=new ExcelMap();
    private JxlExportImportUtils jxlExportImportUtils;
    private CraneInspectReport craneInspectReport;
    private ToolUtil toolUtil=new ToolUtil();
    private MongoConnector mongoConnector=new MongoConnector("craneInspectReportDB","craneInspectReportCollection");
    public void upload(InputStream inputStream,String fileName){
      String documentJson=getMongoStringFromRequest(inputStream,fileName);
      mongoConnector.insertDocument(documentJson);
    }
    public String getMongoStringFromRequest(InputStream inputStream,String fileName){
             String mString;
             excelMap=jxlExportImportUtils.analysisExcel(inputStream);
             List<List<String>> listContents=new ArrayList<List<String>>();
             for(int i=0;i<excelMap.getContents().size();i++){
                 Address address=new Address();
                 address=getAddressFromExcel(excelMap,i);
                 if(address==null){
                 listContents.add(excelMap.getContents().get(i));
                 }else{
                 Long addressId=addressService.findIdByArea(address.getProvince(),address.getCity(),address.getArea());
                 if(addressId==null){
                     //addressId查不到
                 }else{
                 craneInspectReport=transferExcelMapToCraneInspectReportObject(excelMap,i,addressId);
                 mapper.insert(craneInspectReport);
                 }
                 }
             }
               JxlExportImportUtils.createExcel(excelMap.getHeads(),listContents,fileName);
               mString=getDocumentJson(excelMap);
               return mString;
    }
    public String getDocumentJson(ExcelMap excelMap){
        String documentJson="{craneinspectreports:[";
        for(int i=0;i<excelMap.getContents().size()-1;i++){
            String documentJson1="{";
            for(int j=0;j<excelMap.getContents().get(i).size()-1;j++){
                documentJson1+=excelMap.getHeads().get(j)+":'"+excelMap.getContents().get(i).get(j)+"',";
                if(j+1==excelMap.getContents().get(i).size()-1){
                    documentJson1+=excelMap.getHeads().get(j+1)+":'"+excelMap.getContents().get(i).get(j+1)+"'";
                }
            }
            documentJson+=documentJson1+"},";
            if(i+1==excelMap.getContents().size()-1){
                documentJson+=documentJson1+"}";
            }
        }
        documentJson+="]}";
        return documentJson;
    }
    public CraneInspectReport transferExcelMapToCraneInspectReportObject(ExcelMap excelMap,int i,Long addressId){
             Date d=toolUtil.transferStringToDate(excelMap.getContents().get(i).get(10));
             craneInspectReport=new CraneInspectReport();
             craneInspectReport.setReportNumber(excelMap.getContents().get(i).get(0));
             craneInspectReport.setUnitAddress(excelMap.getContents().get(i).get(1));
             craneInspectReport.setAddressId(addressId);
             craneInspectReport.setOrganizeCode(excelMap.getContents().get(i).get(2));
             craneInspectReport.setUserPoint(excelMap.getContents().get(i).get(3));
             craneInspectReport.setSafeManager(excelMap.getContents().get(i).get(4));
             craneInspectReport.setContactPhone(excelMap.getContents().get(i).get(5));
             craneInspectReport.setEquipmentVariety(excelMap.getContents().get(i).get(6));
             craneInspectReport.setUnitNumber(excelMap.getContents().get(i).get(7));
             craneInspectReport.setManufactureUnit(excelMap.getContents().get(i).get(8));
             craneInspectReport.setManufactureLicenseNumber(excelMap.getContents().get(i).get(9));
             craneInspectReport.setManufactureDate(d);
             craneInspectReport.setSpecification(excelMap.getContents().get(i).get(11));
             craneInspectReport.setpNumber(excelMap.getContents().get(i).get(12));
             craneInspectReport.setWorkLevel(excelMap.getContents().get(i).get(13));
             return craneInspectReport;
    }
    private Address getAddressFromExcel(ExcelMap excelMap,int i){
        if(toolUtil.parseAddress(excelMap.getContents().get(i).get(1)).equals("0")){
            return null;
        }else{
        String str[]=toolUtil.parseAddress(excelMap.getContents().get(i).get(1)).split(",");
        Address address=new Address();
        address.setProvince(str[0]);
        address.setCity(str[1]);
        address.setArea(str[2]);
        return address;
        }
    }


}