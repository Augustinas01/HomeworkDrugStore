package helpers;

import constants.Gender;
import constants.MedicineType;
import models.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DataManager {

    private final String databasePath = "jdbc:mysql://localhost:3306/";
    private final String databaseUser = "student";
    private final String databasePass = "codeacademy";

    private static DataManager dataManager_instance = null;
    private HashMap<Integer,Medicine> m;

    private AppCache appCache;

    private String loginName = null;
    private Customer loggedUser = null;

    private BasicConnectionPool connectionPool;
    private Connection connection;

    private DataManager() {
        try {
            connectionPool = BasicConnectionPool.create(databasePath, databaseUser, databasePass);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        appCache = AppCache.getInstance();
    }

    public static DataManager getInstance(){

        if(dataManager_instance == null){
            dataManager_instance = new DataManager();
        }

        return dataManager_instance;

    }

    //region Getters

    public String getLoginName() {
        return loginName;
    }

    //endregion

    //region Setters

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    //endregion


    //region Login
    public boolean login(String username, char[] password){

        boolean l = false;
        connection = connectionPool.getConnection();

        try(PreparedStatement statement = connection.prepareStatement(String.format("select * from classroom.people where first_name = '%s'",username));
            ResultSet user = statement.executeQuery()){

            while(user.next()){
                System.out.println("Checking");
                l = user.getString("user_password").equals(hashPassword(new String(password)));
                if (l){

                    loggedUser = new Customer();

                    loggedUser.setCostumerID(user.getInt("people_id"));
                    loggedUser.setFirstName(username);
                    loggedUser.setLastName(user.getString("last_name"));
                    loggedUser.setEmail(user.getString("email"));
                    loggedUser.setGender(user.getString("gender"));
                    loggedUser.setDateOfBirth(user.getDate("birthday"));
                    appCache.setUser(loggedUser);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            connectionPool.releaseConnection(connection);
            connection = null;
        }
        System.out.println(l);
        return l;
    }


    //region SHA-256

    public static String hashPassword(String text)
    {
        BigInteger number = null;
        // Convert byte array into signum representation
//        BigInteger number = new BigInteger(1, getSHA(text));
        try{
            number = new BigInteger(1, MessageDigest.getInstance("SHA-256").digest(text.getBytes(StandardCharsets.UTF_8)));
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
    //endregion
    //endregion


    public HashMap<Integer, Medicine> getMedicineMap(){

        if(m == null) {
            connection = connectionPool.getConnection();
            m = new HashMap<>();

            try (PreparedStatement statement = connection.prepareStatement("select * from drug_store.medicine");
                 ResultSet medSet = statement.executeQuery()) {

                while (medSet.next()) {

                    System.out.println("Gettin medicine");
                    Medicine med = new Medicine();
                    med.setMedicineID(medSet.getInt("medicine_id"));
                    med.setManufacturerID(medSet.getInt("manufacturer_id"));
                    med.setTitle(medSet.getString("title"));
                    med.setDescription(medSet.getString("medicine_description"));
                    med.setMedicineType(medSet.getString("medicine_type"));
                    med.setPrice(medSet.getDouble("price"));
                    med.setCreated(medSet.getDate("creation_date"));
                    med.setChildrenSafe(medSet.getBoolean("isChildrenSafe"));
                    m.put(med.getMedicineID(), med);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connectionPool.releaseConnection(connection);
                connection = null;
            }
        }

        return m;

    }

    public void putTransaction(Transaction t){

        connection = connectionPool.getConnection();

        appCache.getShoppingCart().forEach((m,q) -> {
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement(String.format("insert into drug_store.transaction_medicine (transaction_guid,medicine_id,quantity)" +
                                    "values ('%s','%s','%s')",
                            t.getTransactionID(), m.getMedicineID(),q))) {
                preparedStatement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        try (PreparedStatement preparedStatement = connection
                .prepareStatement(String.format("insert into drug_store.transactions (transaction_guid,costumer_id,total_price,discount)" +
                                                  "values ('%s','%s','%s','%s')",
                        t.getTransactionID(), t.getCostumerID(),t.getTotalPrice(),t.getDiscount()))) {
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            connection = null;
        }


    }


    //region Database fillers
    public void fillComponents(){

        connection = connectionPool.getConnection();

        List<String> binder = List.of("Polymers","CMC Na","Gelatin","Gom Nature","Acacia","Tragakan","Guar","Pectin","Amylum",
                "Pregelatin Amylum","Sucrose","Others","Corn syrup","PEG","Na Alginat","Magnesium aluminum silicate");
        List<String> disitergant = List.of("dry manihot starch", "gelatinum", "sodium alginate", "Amylum", "Amylum 1500",
                "Avicel (microcrystalline cellulose)", "Solka floc", "Alginic acid", "Explotab (sodium starch glycolate)",
                "Gom guar", "Policlar AT (Crosslinked PVP)", "Amberlite IPR 88", "Methylcellulose", "CMC", "HPMC");
        List<String> filler = List.of("Saccharum lactis", "Amylum manihot", "calcium phospas", "calcii carbonas");
        List<String> fillerBinder = List.of("amylum", "microcrystaline celulose", "calcium carbonate", "mannitol", "sucrose", "dextrose", "sorbitol");
        List<String> lubricant = List.of("5% talkum", "Magnesium stearate", "Acidum Stearicum", "talc", "wax", "paraffin liquid",
                "boric acid", "sodium lauryl sulfate", "magnesium lauryl sulfate", "glyceryl behapate");
        List<String> glidant = List.of("colloidal pyogenic silica", "steareate metal", "stearic acid", "talc", "amylum",
                "sodium benzoate", "sodium chloride", "sodium and magnesium lauryl sulfate", "PEG 4000","PEG 6000");
        List<String> dyesPigment = List.of("erytrosine (red 3)", "allura red AC (red 40)", "tartazine (yellow 5)",
                "sunset yellow (yellow 6)", "brilliant blue (blue 1)");
        List<String> sweetenerNatural = List.of("mannitol", "lactose", "sucrose", "dextrose");
        List<String> sweetenerSynthetic = List.of("saccarine", "cyclamate", "aspartame");
        List<String> preservatives = List.of("Benzalkomnium chloride", "Benxyl alcohol", "Cetyltrimethyl ammonium bromide",
                "Chorhexidine gluconate", "Imidazolynyl urea", "Nitromersol","Phenol");
        List<String> antioxidant = List.of("A-tocopherol acetate","Ascorbic acid", "Butylated hydroxytoluene","Monothioglycerol",
                "Sodium sulfite","Cysteine", "Propyl galate", "Thiourea");
        List<String> suspendingAgent = List.of("Acasia (Pulvis gummi arabici)", "Chondrus", "Tragacanth", "Algin");
        List<String> coatingAgent = List.of("HPMC", "MHC", "ethyl cellulose", "HPC", "povidone", "Na-CMC", "PEG", "active polymers", "cellulose derivate");
        List<String> flavoringAgent = List.of("glycerin", "glucose", "citric acid", "peppermint oil", "saccharin", "orange oil");

        HashMap<String, List<String>> types = new HashMap<>();

        types.put("binder",binder);
        types.put("disitergant",disitergant);
        types.put("filler",filler);
        types.put("fillerBinder",fillerBinder);
        types.put("lubricant", lubricant);
        types.put("glidant",glidant);
        types.put("dyesPigment", dyesPigment);
        types.put("sweetenerNatural", sweetenerNatural);
        types.put("sweetenerSynthetic", sweetenerSynthetic);
        types.put("preservatives", preservatives);
        types.put("antioxidant", antioxidant);
        types.put("suspendingAgent", suspendingAgent);
        types.put("coatingAgent", coatingAgent);
        types.put("flavoringAgent", flavoringAgent);



        types.forEach((k,v) ->{
            for(String title:v) {
                try (PreparedStatement preparedStatement = connection
                        .prepareStatement(String.format("insert into drug_store.components (title,component_type) values ('%s','%s')", title, k))) {
                    if(!preparedStatement.execute()) {
                        System.out.printf("Drug: %s,%s has been added!%n", title, k);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    connectionPool.releaseConnection(connection);

                }
            }
        });
        connection = null;
    }


    public void fillMedicine(){

        connection = connectionPool.getConnection();

       try(BufferedReader br = new BufferedReader(new FileReader("src/resources/data/medicine"));
           BufferedReader br2 = new BufferedReader(new FileReader("src/resources/data/medecineTypes"))){


           String[] medicineTypes = br2.readLine().split(",");
           String line,medicineDescription ;
           String medicineName = null;
           while((line = br.readLine()) != null){
               if(!line.trim().equals("") && Character.isDigit(line.trim().charAt(0)) ){
                   medicineName = line.trim().substring(3).trim();

               }
               if(!line.trim().equals("") && line.trim().charAt(0) == medicineName.charAt(0)){

                   medicineDescription = line.trim();

                   ThreadLocalRandom r = ThreadLocalRandom.current();
                   int manufacturerID = r.nextInt(1,12);
                   String medicineType = medicineTypes[r.nextInt(medicineTypes.length)];

                   int price = r.nextInt(1000);
                   String creationDate = String.format("%s-%s-%s",r.nextInt(1500,2020),r.nextInt(1,12),r.nextInt(1,31));
                   int childrenSafe = r.nextInt(2);


                   try (PreparedStatement preparedStatement = connection
                           .prepareStatement(String.format(
                                   """
                                           insert into drug_store.medicine (title,manufacturer_id,medicine_type,medicine_description,components,price,creation_date,isChildrenSafe)
                                            values ('%s','%s','%s','%s','%s','%s','%s')""",
                                                medicineName,manufacturerID, medicineType, medicineDescription,price,creationDate,childrenSafe))) {
                       preparedStatement.execute();

                   } catch (SQLException e) {
                       e.printStackTrace();
                   } finally {
                       connectionPool.releaseConnection(connection);
                   }
               }

           }


       }catch (IOException e){
           e.printStackTrace();
       }
        connection = null;
    }

    public void fillManufacturers(){
        connection = connectionPool.getConnection();

        String line;
        try(BufferedReader br = new BufferedReader(new FileReader("src/resources/data/manufacturers"))){
            while((line = br.readLine()) != null){

                String[] manInfo = line.split(";");
                try (PreparedStatement preparedStatement = connection
                        .prepareStatement(String.format("insert into drug_store.manufacturer (title,address,country) values ('%s','%s','%s')",
                                manInfo[0], manInfo[2],manInfo[1]))) {
                    preparedStatement.execute();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    connectionPool.releaseConnection(connection);
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        connection = null;
    }
    //endregion





}
