import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform =
      MethodChannel('com.example.method_channel_demo/battery');

  String _betteryStatus = 'Unknown status';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              _betteryStatus,
              style: Theme.of(context).textTheme.headline4,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _captureStatus,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }

  Future<void> _captureStatus() async {
    try {
      var map = {'id': 23, 'name': 'Prakas'};
      final int result = await platform.invokeMethod('getBatteryLevel', map);

      _betteryStatus = 'Battery level is $result';
    } on PlatformException catch (e) {
      _betteryStatus = ' Failed to get Bettery level :${e.toString()}';
    }

    setState(() {});
  }
}
