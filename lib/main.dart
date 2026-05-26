import 'package:ahbit/create_habit.dart';
import 'package:flutter/material.dart';

final navigatorKey = GlobalKey<NavigatorState>();

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const HabitListPage(),
    );
  }
}

class HabitListPage extends StatelessWidget {
  const HabitListPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: const Text("Ahbit"),
      ),
      body: Column(
        children: [
          Habit(habitName: "aaa"),
          Habit(habitName: 'bbb'),
          Habit(habitName: 'ccc')
        ],
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          final result = await Navigator.of(context).push<String>(
              MaterialPageRoute(builder: (context) => const CreateHabitPage()));

          if (result != null) {
            ScaffoldMessenger.of(navigatorKey.currentContext!)
                .showSnackBar(SnackBar(content: Text('returned $result')));
          }
        },
        tooltip: 'None',
        child: const Icon(Icons.add),
      ),
    );
  }
}

class Habit extends StatefulWidget {
  const Habit({super.key, required this.habitName});
  final String habitName;

  @override
  State<Habit> createState() => _HabitState();
}

class _HabitState extends State<Habit> {
  bool _isChecked = false;

  void _toggleCheckbox(bool? value) {
    setState(() {
      _isChecked = value!;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Checkbox(value: _isChecked, onChanged: _toggleCheckbox),
        Text(widget.habitName)
      ],
    );
  }
}
