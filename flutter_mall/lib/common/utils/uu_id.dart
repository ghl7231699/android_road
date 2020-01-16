import 'package:uuid/uuid.dart';

var uuid = new Uuid();

String getUUID() {
  return uuid.v4();
}
