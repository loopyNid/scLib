SimpleSynth {
	classvar <>density=200;//a class variable are values that are shared by all objects in the class with the signs <> getter and setter (getting and setting the value of that variable)
	
	*ar {| doom=1| //class methods are specified with an asterisk (*) before the method name.
		
		^SinOsc.ar( //To return a different values write the ^ sign before the statement whose value will be returned (Wilson 2011 p.170)
				LFNoise1.ar(200, doom*density, 400), 0, 0.5)
		
	}

}

//Put this in your SCClassLibrary or Extensions fodler and recompile class library. 
//Then run the following

//SimpleSynth.density = 400;
//{SimpleSynth.ar(2)}.play;