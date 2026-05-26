import 'package:flutter/material.dart';

class CreateHabitPage extends StatelessWidget {
  const CreateHabitPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
          backgroundColor: Theme.of(context).colorScheme.inversePrimary,
          title: const Text("CreateHabit")),
      body: CreateHabitComponentAggregate(),
    );
  }
}

class CreateHabitComponentAggregate extends StatelessWidget {
  const CreateHabitComponentAggregate({super.key});

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(builder: (context, constraint) {
      return ConstrainedBox(
        constraints: BoxConstraints.tight(
            Size(constraint.maxWidth, constraint.maxHeight)),
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              TextField(),
              const SizedBox(
                height: 16.0,
              ),
              ElevatedButton(
                  onPressed: () {
                    Navigator.of(context).pop();
                  },
                  child: const Text("Create Habit"))
            ],
          ),
        ),
      );
    });
  }
}
