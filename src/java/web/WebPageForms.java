package web;

import assets.Constants;
import errors.Errors;
import initialize.Application;
 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

public class WebPageForms {
    
    private static String className = "WebPageForms";
    
    public static ArrayList getAllForms (int group_id, HttpServletRequest request) {
         
        ArrayList formList  = new ArrayList();
        try {
            Statement st = Constants.dbConnection.createStatement();
            String query = null;
           
            Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
            
            if (group_id > 0) {
                query = " SELECT * FROM  `" +  Constants.db_database  +  "`.`forms` "
                      + " WHERE ENABLED = 1 AND GROUPID = " + group_id + " AND ADMINID = " + id_key[0];
                
                
                
            } else {
                query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`forms`"
                      + " WHERE ENABLED = 1 AND ADMINID = " + id_key[0];
            } 
            
            ResultSet rs = st.executeQuery(query);
            Object[] obj = null;
            
            while (rs.next()) { 
                obj = new Object [3];
                obj[0] = rs.getInt("ID");
                obj[1] = rs.getString("NAME");
                obj[2] = rs.getString("CDATE");
                formList.add(obj);
            }
            
            st.close();
            rs.close();
        } catch (Exception ex ) {
            Errors.setErrors(className + " / isAccessAllowed " + ex.toString());
        }         
        
        return formList;
    }
         
    public static ArrayList getAllGroups (HttpServletRequest request) {
    
        Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
        ArrayList groupList  = new ArrayList ();
        try {
            Statement st = Constants.dbConnection.createStatement();
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`devices_group` WHERE  ADMINID = " + id_key[0];
            ResultSet rs = st.executeQuery(query);
            Object [] obj = null;
            while (rs.next()) { 
                obj = new Object[3];
                obj[0] = rs.getInt("ID");
                obj[1] = rs.getString("NAME");
                
                groupList.add(obj);
            }
            st.close();
            rs.close();
        } catch (Exception ex ) {
            Errors.setErrors("WebPageUsers / isAccessAllowed " + ex.toString());
        }  
        
        return  groupList;
    }
    
    /**
     * SetID if you wish to use the specific id otherwise use 0;
     * Id is used when we are updating the old Record 
     * @param id
     * @param request 
     */
    public static void setForm (int id , HttpServletRequest request) {
        
        
        String query  = "";
        int formid = 0;
        Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
        
        PreparedStatement smtp = null;
        String formname =  request.getParameter(WebConstants.form_name_post);
        
        
        String gidst = request.getParameter(WebConstants.assignedgroups_post);
        int groupid  = Integer.parseInt(gidst) ;     
        
        
        
        String form_url = "";
        try {
            form_url =  request.getParameter(WebConstants.form_name_url);
        } catch (Exception ex) {
            Errors.setErrors( " / createNewForm "  + ex.toString());
        }
        
      
        try {
           
            if (id  > 0) {
            
                   query = " UPDATE  `" +  Constants.db_database  +  "`.`forms` SET "
                         + " NAME = ? ,  UDATE = ? , ENABLED = 1, URL = ? , GROUPID  = ?  "
                         + " WHERE ID = " + id  + " AND ADMINID = " + id_key[0];
                         
                    
                   
                   int time = (int)(System.currentTimeMillis()/1000);
                   smtp  = Constants.dbConnection.prepareStatement(query);
                   
                   smtp.setString(1, formname);
                   smtp.setInt(2, time);
                   smtp.setString(3, form_url);
                   smtp.setInt(4,groupid);
                   smtp.execute();
                   formid = id;
                   smtp.close();
            } else {
            
                  query = "INSERT INTO `" +  Constants.db_database  +  "`.`forms` "
                         + " (NAME, UDATE, ENABLED, URL, ADMINID, GROUPID) "
                         + " VALUES "
                         + " (?, ? , 1, ? , ?, ? )" ;

                   int time = (int)(System.currentTimeMillis()/1000);
                   smtp  = Constants.dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                   smtp.setString(1, formname);
                   smtp.setInt(2, time);
                   smtp.setString(3, form_url);
                   smtp.setInt(4, Integer.parseInt(id_key[0].toString()));
                   smtp.setInt(5,groupid);
                   
                   if (smtp.executeUpdate() > 0) {
                       ResultSet generatedKeys = smtp.getGeneratedKeys();
                       if ( generatedKeys.next() ) {
                            formid = generatedKeys.getInt(1);
                       }
                       generatedKeys.close();
                   }

                   smtp.close();
            }
               
        } catch (Exception ex) {
             Errors.setErrors(className + " / createNewForm 1 " + ex.toString());
        }
        
        if (formid > 0) {
            setSection  (formid, request);
        }
    }
    
    private static void setSection  (int formid,  HttpServletRequest request) {
            try { 
                    String[] sectionidents =  request.getParameterValues(WebConstants.sectionident_post);
                    Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
                    int sectionid = 0;
                    String sectionname;
                    PreparedStatement smtp = null;
                    if (sectionidents.length > 0 ) {
                     
                    String query = "";    
                    
                    
                   
                         for (int i = 0 ; i < sectionidents.length ; i++) {
                         
                                    sectionname = request.getParameter(WebConstants.sectionname_post + "[" + sectionidents[i]+ "]"); 

                                   if (sectionidents[i].length() < 12) {
                                       sectionid = Integer.parseInt(sectionidents[i]);

                                       query = "UPDATE  `" +  Constants.db_database  +  "`.`forms_sections` SET "
                                               + " FID = ?,  FP = 0, NAME = ? "
                                               + " WHERE ID = " + sectionid + " AND ADMINID = " + id_key[0];

                                       smtp  = Constants.dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                                               smtp.setInt(1, formid);
                                               smtp.setString(2,  sectionname);
                                               
                                       if (!(smtp.executeUpdate() > 0)) {
                                            sectionid = 0;  
                                       }           

                                   } else {
                                         query = "INSERT INTO `" +  Constants.db_database  +  "`.`forms_sections` "
                                                      + " (FID, FP, NAME, ADMINID) "
                                                      + " VALUES "
                                                      + " (?, 0, ? , ? )" ;

                                               smtp  = Constants.dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                                               smtp.setInt(1, formid);
                                               smtp.setString(2,  sectionname);
                                               smtp.setInt(3, Integer.parseInt(id_key[0].toString()));

                                                if (smtp.executeUpdate() > 0) {
                                                    ResultSet generatedKeys = smtp.getGeneratedKeys();
                                                    if ( generatedKeys.next() ) {
                                                          sectionid  = generatedKeys.getInt(1);
                                                    }
                                                    generatedKeys.close();
                                               }   

                                   }


                                   try {
                                       smtp.close();
                                   } catch (Exception ex) {
                                        Errors.setErrors(className + " / createNewForm 2 " + ex.toString());
                                   }
                                       
                                   if (sectionid > 0) {
                                        setFields (formid, sectionidents[i],  sectionid, request);
                                   }
                                 }

                            

                    }
                 } catch (Exception ex) {
                                  Errors.setErrors(className +  " / setSection " + ex.toString());
                 }
    }
    
    private static void setFields (int formid, String sectionident , int sectionid,  HttpServletRequest request) {
        
        try {
        
        
            String [] fieldidents = request.getParameterValues(WebConstants.fieldident_post);
            Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
            String query = null;
            PreparedStatement smtp = null;
             
            for (int fi = 0 ; fi < fieldidents.length ; fi++ ) {
                                           
                String fieldident = fieldidents[fi];
                String[] fieldIdentSplit = fieldident.split("_");
                                        
                if (fieldIdentSplit[0].equals(sectionident)) {
                    
                    int fieldid = 0;
                    
                    if (fieldIdentSplit[1].length() < 12) {
                        fieldid = Integer.parseInt(fieldIdentSplit[1]);
                    }
                    
                    
                     
                   String fieldName  = null;
                   String fieldDVal  = "";
                   int    fieldType  = 1;
                   int    fieldMand  = 0;
                   int    fieldDISPL = 0;

                   try {
                       fieldName =  request.getParameter(WebConstants.fieldName_post + "[" + fieldidents[fi] + "]");
                   } catch( Exception ex) {
                       Errors.setErrors(className + " / setFields  / ignore " + ex.toString());
                   }

                   try {
                       fieldDVal =  request.getParameter(WebConstants.fieldDefault_post + "[" + fieldidents[fi] + "]");
                   } catch( Exception ex) {
                       Errors.setErrors(className + " / setFields  / ignore " + ex.toString());
                   }

                   try {
                       fieldType = Integer.parseInt(request.getParameter(WebConstants.field_type_post + "[" + fieldidents[fi] + "]"));
                   } catch( Exception ex) {
                       Errors.setErrors(className + " / setFields  / ignore " + ex.toString());
                   }

                   try {
                       if (request.getParameter(WebConstants.mandatory_post + "[" + fieldidents[fi] + "]").equals("on")) {
                           fieldMand = 1;
                       }
                   } catch( Exception ex) {
                       Errors.setErrors(className + " / setFields  / ignore " + ex.toString());
                   }

                   try {
                       if (request.getParameter(WebConstants.visible_post + "[" + fieldidents[fi] + "]").equals("on")) {
                          fieldDISPL = 1;
                       }
                   } catch( Exception ex) {
                       Errors.setErrors(className + " / setFields  / ignore " + ex.toString());
                   }
                   
                   

                try {
                    
                    if (fieldid > 0) {

                        if (fieldName != null && fieldidents[fi].contains(sectionident) ) {

                                 query = "UPDATE `" +  Constants.db_database  +  "`.`forms_fields` SET "
                                        + " SID = ? , FID = ?, NAME = ? , DVAL = ? , TYPE = ?, MAND = ?, DISPL = ? "
                                        + " WHERE ID = " + fieldid + " AND ADMINID = " + id_key[0] ;
                                 
                                 smtp  = Constants.dbConnection.prepareStatement(query);
                                 smtp.setInt(1, sectionid );
                                 smtp.setInt(2, formid);
                                 smtp.setString(3, fieldName);
                                 smtp.setString(4, fieldDVal);
                                 smtp.setInt(5, fieldType);
                                 smtp.setInt(6, fieldMand);
                                 smtp.setInt(7, fieldDISPL);
                                 
                                 smtp.execute();
                                 smtp.close();
                            }
                    } else {
                             if (fieldName != null && fieldidents[fi].contains(sectionident) ) {

                                 query = "INSERT INTO `" +  Constants.db_database  +  "`.`forms_fields` "
                                        + " (SID, FID, NAME, DVAL, TYPE, MAND, DISPL, 	ADMINID ) "
                                        + " VALUES "
                                        + " (?, ?, ?, ?, ?, ?, ? , ? )" ;

                                 smtp  = Constants.dbConnection.prepareStatement(query);
                                 smtp.setInt(1, sectionid );
                                 smtp.setInt(2, formid);
                                 smtp.setString(3, fieldName);
                                 smtp.setString(4, fieldDVal);
                                 smtp.setInt(5, fieldType);
                                 smtp.setInt(6, fieldMand);
                                 smtp.setInt(7, fieldDISPL);
                                 smtp.setInt(8, Integer.parseInt(id_key[0].toString()));
                                 smtp.execute();
                                 smtp.close();
                            }

                    }
                } catch (Exception ex) {
                      Errors.setErrors(className  + " / createNewForm 2 " + ex.toString());
                }
              }
            }
        } catch (Exception ex) {
              Errors.setErrors(className  + " / createNewForm 3 " + ex.toString());
        }
        
    }
    
    public static String[] getSingleFormValues (int formid, HttpServletRequest request) {
        String[] result = null ;
        try {
            Statement st = Constants.dbConnection.createStatement();
            Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
            String query = "SELECT * FROM `" +  Constants.db_database  +  "`.`forms` WHERE ID = " + formid + " AND ADMINID =  " + id_key[0];
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
               result = new String [3];
               result[0] = rs.getString("NAME");
               result[1] = rs.getString("URL");
               result[2] = rs.getInt("GROUPID") + "";
            }
            st.close();
            rs.close();
        } catch (Exception ex ) {
            Errors.setErrors(className + " / isAccessAllowed " + ex.toString());
        }  
        return result ;
    }
    
    
    
    public static ArrayList getSectionsOfSingleForm (int formid, HttpServletRequest request) {
         
       ArrayList result = new ArrayList ();
       
       try {
           Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
           String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`forms_sections` WHERE FID = " + formid + " AND ADMINID =  " + id_key[0].toString();
           Statement st = Constants.dbConnection.createStatement();
           ResultSet rs = st.executeQuery(query);
          
           Object[] obj = null;
           while (rs.next()) { 
                obj    = new Object [2];
                obj[0] = rs.getInt("ID");
                obj[1] = rs.getString("NAME");
                
                result.add(obj);
           }
           
           st.close();
           rs.close();
           
        } catch (Exception ex ) {
            Errors.setErrors(className + " / getSectionsOfSingleFom " + ex.toString());
        }        
         
        return result;
    }
    
    public static ArrayList getSectionFieldsArray (int sectionid, int formid , HttpServletRequest request) {
        Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
       ArrayList result = new ArrayList ();
       try {
          
           String query = "SELECT * FROM  `" +  Constants.db_database  
                   +  "`.`forms_fields` WHERE SID = " + sectionid + " AND FID = "  + formid 
                   + " AND ADMINID = " + id_key[0].toString();
           
           Statement st = Constants.dbConnection.createStatement();
           ResultSet rs = st.executeQuery(query);
           
           Object[] obj = null;
           
           while (rs.next()) { 
               
               obj    = new Object[6];
               obj[0] = rs.getInt("ID");
               obj[1] = rs.getString("NAME");
               obj[2] = rs.getString("DVAL");
               obj[3] = rs.getString("TYPE");
               obj[4] = rs.getString("MAND");
               obj[5] = rs.getString("DISPL");
               result.add(obj);
               
           }
           
           st.close();
           rs.close();
           
        } catch (Exception ex ) {
            Errors.setErrors(className + " / getSectionFieldsArray " + ex.toString());
        }        
         
        return result;
    }
    
    public static void deleteForm (int formid, HttpServletRequest request) {
        String query  = "";
        try {
                Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
                query = "DELETE FROM  `" +  Constants.db_database  +  "`.`forms` WHERE ID  =  " + formid + " AND ADMINID = " + id_key[0];
                Statement st =Constants.dbConnection.createStatement();
                st.execute(query);
                query = "DELETE FROM  `" +  Constants.db_database  +  "`.`forms_fields` WHERE FID = " + formid  + " AND ADMINID = " + id_key[0];
                st.execute(query);
                query = "DELETE FROM  `" +  Constants.db_database  +  "`.`forms_sections` WHERE FID  =  " + formid  + " AND ADMINID = " + id_key[0];
                st.execute(query);
                st.close();
        } catch (Exception ex) {
                Errors.setErrors(className + " / deleteForm  " + ex.toString());
        }
    }
    
    public static void editForm (HttpServletRequest request) {
        
        int formid = 0;
        try {
            formid = Integer.parseInt(request.getParameter("formid").toString().trim()); 
        } catch (Exception ex) {
            Errors.setErrors(className + " / editForm " + ex.toString());
        }
        
        if (formid > 0) {
            setForm (formid, request);
        }
        
    }
    
    
}
