export class Person {
  public 'ns:name': string;
  public 'ns:surname': string;
  public 'ns:email': string;
  public 'ns:phone': string;
  public 'ns:institution': Institution;
}

export class Institution {
  public 'ns:name': string;
  public 'ns:address': Address;
}

export class Address {
  public 'ns:city': string;
  public 'ns:streetNumber': number;
  public 'ns:floorNumber': number;
  public 'ns:street': string;
  public 'ns:country': string;
}
