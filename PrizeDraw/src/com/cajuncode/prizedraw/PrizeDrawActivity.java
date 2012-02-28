package com.cajuncode.prizedraw;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PrizeDrawActivity extends Activity implements OnInitListener {
    private static final int MY_DATA_CHECK_CODE = 11759;
    
    private EditText guests;
    private TextToSpeech mTts;
    private Random generator;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        generator = new Random();
        
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
        
        guests = (EditText) findViewById(R.id.editText1);
        
        Button b = (Button) findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				int winner = generator.nextInt(Integer.parseInt(
						guests.getText().toString())) + 1;
				String message = "The winner is number " + winner;
				mTts.speak(message, TextToSpeech.QUEUE_ADD, null);
				Toast.makeText(PrizeDrawActivity.this, message, 2000).show();
				
			}
		});
    }
    
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                mTts = new TextToSpeech(this, this);
            } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(
                    TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }
	public void onInit(int status) {
		// TODO Auto-generated method stub
		
	}
}