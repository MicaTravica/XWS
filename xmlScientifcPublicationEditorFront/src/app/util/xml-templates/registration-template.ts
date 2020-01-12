export const registrationTemplate =
    ( name: string, surname: string,
      email: string, phone: string) => 
        `<?xml version="1.0" encoding="UTF-8"?>
        <person xmlns="http://www.uns.ac.rs/Tim1"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://www.uns.ac.rs/Tim1 file:/home/dusanb/Documents/Semestar7/XML_webServisi/projekat/XWS/person.xsd">
            <name>${ name }</name>
            <surname>${ surname }</surname>
            <email>${ email }</email>
            <phone>${ phone }</phone>
            <institution id="dasdas">
                <name>FTN</name>
                <address>
                    <city>Novi Sad</city>
                    <street>neka leva</street>
                    <country>Srbija</country>
                </address>
            </institution>
            <expertise>IT</expertise>
        </person>`;

