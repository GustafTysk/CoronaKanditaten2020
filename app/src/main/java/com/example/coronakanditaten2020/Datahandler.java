package com.example.coronakanditaten2020;

 class Datahandler {


     Datahandler() {
    }

    Dataobj  SendDataRequest (String Tag , Dataobj Data){
        String datatyp= "message";

        Dataobj ReturnData= new Dataobj(datatyp);




        return ReturnData;
    }

    String SendData (String Tag, Dataobj Data){
        return "data sent";
    }

}
