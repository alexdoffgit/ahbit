import 'package:drift/drift.dart';
import 'package:ahbit/database/tables/habit.dart';

class HabitMetadata extends Table {
  Int64Column get id => int64().autoIncrement()();
  Int64Column get habitId => int64().references(Habit, #id)();
  TextColumn get weekday => text()();
  DateTimeColumn get timeStart => dateTime()();
  DateTimeColumn get timeEnd => dateTime().nullable()();
  Int64Column get linkedHabit => int64().references(Habit, #id).nullable()();
  Int64Column get version => int64().clientDefault(() => BigInt.from(1))();
}
