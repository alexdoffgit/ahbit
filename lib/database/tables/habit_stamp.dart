import 'package:drift/drift.dart';
import 'package:ahbit/database/tables/habit.dart';

class HabitStamp extends Table {
  Int64Column get id => int64().autoIncrement()();
  Int64Column get habitId => int64().references(Habit, #id)();
  BoolColumn get checked => boolean()();
  DateTimeColumn get timestamp => dateTime().withDefault(currentDateAndTime)();
  Int64Column get version => int64().clientDefault(() => BigInt.from(1))();
}
