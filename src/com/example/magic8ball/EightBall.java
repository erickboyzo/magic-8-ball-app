package com.example.magic8ball;

import java.util.Random;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class EightBall extends ActionBarActivity implements SensorEventListener {
	private TextView message;
	MediaPlayer pop;
	TextToSpeech ts;
	SensorManager sensorManager;
	int sensorCounter = 1;
	int sensorCounter1 = 0;
	Vibrator v;
	String[] responses = { "Why Of Course", "Ask Me If I Care",
			"Dumb Question Ask Another", "Forget About It", "Get A Clue",
			"In Your Dreams", "Not A Clue", "Not A Chance", "Obviously",
			"Where There's A Will There's A Way", "That's Ridiculous", "May Baby Jesus Help You",
			"I Believe You Can", "Whatever Floats Your Boat", "Oh Lord",
			"Yeah And I'm The Pope", "Yeah Right", "You Wish",
			"You've Got To Be Kidding Me" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eight_ball);
		message = (TextView) findViewById(R.id.message);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_UI);
		pop = new MediaPlayer();
		pop = MediaPlayer.create(this, R.raw.bubble_pop);
		v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		ts = new TextToSpeech(getApplicationContext(),
				new TextToSpeech.OnInitListener() {
					@Override
					public void onInit(int status) {

					}
				});

		if (savedInstanceState != null) {
			String retrievedResponse = savedInstanceState.getString("Response");
			if (retrievedResponse != null) {
				message.setText(retrievedResponse);
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle state) {
		String savedResponse = message.getText().toString();
		state.putString("Response", savedResponse);
		super.onSaveInstanceState(state);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		float z = event.values[2];
		if (z > 9 && z < 10) {

			if (sensorCounter == 0) {
				String toSpeak = speakText(responses);
				message.setText(toSpeak);
				ts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
				sensorCounter++;
				sensorCounter1 = 0;
			}

		} else if (z > -10 && z < -9) {
			if (sensorCounter1 == 0) {

				pop.start();
				v.vibrate(500);
				message.setText("");
				sensorCounter1++;
				sensorCounter = 0;
			}

		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_UI);
	}

	public String speakText(String[] respond) {
		Random randomeGenerator = new Random();
		int randomInt = randomeGenerator.nextInt(20);
		String toSpeak = respond[randomInt].toString();
		return toSpeak;

	}

}
