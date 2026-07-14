import 'package:drift/drift.dart';

class Habit extends Table {
  Int64Column get id => int64().autoIncrement()();
  TextColumn get habitName => text()();
  Int64Column get version => int64().clientDefault(() => BigInt.from(1))();
  BoolColumn get isDeleted => boolean().clientDefault(() => false)();
}
