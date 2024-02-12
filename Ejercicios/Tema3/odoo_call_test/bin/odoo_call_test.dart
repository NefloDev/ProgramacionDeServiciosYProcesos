import 'package:odoo_rpc/odoo_rpc.dart';
import 'dart:convert' as convert;

import 'Gender.dart';
import 'user.dart';

const odooServerURL = 'http://localhost:8069';
final client = OdooClient(odooServerURL);

void main(List<String> args) async{
  getUsers().then((value) => value.forEach((element) {print(element.toString());}));
  Future<String> created = createUser(User("Tupu", "fakeEmail@odoo.com", Gender.male, DateTime(1970, 01, 01)));
  created.then((value) {
      if(value != ""){
        print("User $value was created");
      }else{
        print("User couldn't be created");
      }
  });
}

Future<List<User>> getUsers() async{
  List<User> users = [];
  try{
    await client.authenticate('base_de_datos', 'alejo', 'alejo');
    final res = await client.callKw({
      'model': 'hr.employee',
      'method': 'search_read',
      'args': [],
      //Selección de campos a mostrar
      'kwargs':{
        'fields': ['id', 'name', 'private_email', 'active', 'barcode', 'gender',
          'birthday', 'work_email', 'phone', 'marital', 'driving_license']
      }
    });
    //Obtención de resultados en formato JSON
    for(var result in res){
      var temp = convert.jsonEncode(result);
      users.add(User.fromJson(convert.jsonDecode(temp)));
    }
  }on OdooException catch(e){
    print(e);
  }
  return users;
}

Future<String> createUser(User user) async{
  String created;
  try{
    await client.authenticate('base_de_datos', 'alejo', 'alejo');
    await client.callKw({
      'model': 'hr.employee',
      'method': 'create',
      //Argumentos pasados como cuerpo de json
      'args': [
        {
          'name': user.name,
          'private_email': user.privateEmail,
          'active': user.active,
          'barcode': user.barcode,
          'gender': user.gender.name,
          'birthday': user.getBirthDate(),
          'work_email': user.workEmail,
          'phone': user.phone,
          'marital': user.marital.name,
          'driving_license': user.drivingLicense
        }
      ],
      'kwargs':{}
    });
    created = user.name;
  }on OdooException catch(e) {
    print(e);
    created = "";
  }
  return created;
}