// play{Blip.ar([WhiteNoise.kr(10)*5, WhiteNoise.kr(10)*(SinOsc.ar(50) * 10)])};

// play{SinOsc.ar([WhiteNoise.kr(10)*200, WhiteNoise.kr(10)*(SinOsc.ar(50) * 10)])};

// play{Blip.ar([10,10]) + Blip.ar([20,20])}

// play{BrownNoise.ar([0.01, 0.01])}

// play{Impulse.ar(Impulse.kr(10) * 300)}

// play{Saw.ar()};

// play{LFPar.ar([45, 45]) * LFTri.ar(90)};

play{SinOsc.ar(449)}

(
SynthDef(\NID,{arg freq = 300, freqK = 10, vol=0.6;
	var src;
	var env;
	var envgen;
	env = Env.perc(0.001, 1, 0.6, -4);
	envgen = EnvGen.kr(env, gate:1, doneAction:2);
	src = SinOsc.ar(freq , 0, envgen * vol);
	Out.ar([1, -1],src)
}).add;
)

// (
// SynthDef(\NID1,{arg vol = 0.7;
// 	//envelope
// 	var env;
// 	var src;
// 	var envgen;
// 	env = Env.perc(0.001, 1, 0.6, -4);
// 	envgen = EnvGen.kr(env, gate:1, doneAction:2);
// 	src = Blip.ar([(ClipNoise.kr(10)*5) * envgen]);//, (WhiteNoise.kr(10)*5) * envgen]);
// 	Out.ar([1, -1],
// 		FreeVerb.ar(src, 0.33, 2))
// }).add;
// )

(
SynthDef(\NID3,{arg freq= 45, vol=0.6;
	//envelope
	var env;
	var src;
	var envgen;
	env = Env.perc(0.2, 2, 1, -4);
	envgen = EnvGen.kr(env, gate:1, doneAction:2);
	src = LFPar.ar([freq, freq], 0, vol * envgen) * LFTri.ar(freq * 2);
	Out.ar(0,
		FreeVerb.ar(src, 0.33, 1, 1))
}).add;
)

(
SynthDef(\NID4,{arg freq = 300, vol=0.6;
	var src;
	var env;
	var envgen;
	env = Env.perc(0.001, 1.6, 0.6, -4);
	envgen = EnvGen.kr(env, gate:1, doneAction:2);
	src = SinOsc.ar(freq , 0, envgen * vol) + Saw.ar(freq , 0, envgen * vol);
	Out.ar([1, -1],src)
}).add;
)

Synth(\NID4)

r = Routine({
	
	j = Scale.phrygian;
	
	k = Prand([0.35, 0.7, 1.05, 1.4], inf);
	
	~nid  = Pbind(
		\instrument, \NID,
		\degree, Prand([0, 1, 3, 5, 7, 9], inf),
		\scale, Pfunc({ j }, inf),
		\root, -3,
		\dur, Prand([k/2, 0, k/4], inf),
		\vol, 0.25
	).play;

	~nid3 = Pbind(
		\instrument, \NID3,
		\degree, Prand([0, 1, 3, 5, 7, 9], inf),
		\scale, Pfunc({ j }, inf),
		\root, -15,
		\vol, 0.2,
		\dur, Prand([0, 0.35, 1.4], inf)
	).play;

	~nid4 = Pbind(
		\instrument, \NID4,
		\freq, 55,
		\dur, 0.7,
		\vol, 0.7
	).play;
	// 0.35.wait;
	
	// ~nid1 = Pbind(
	// 	\instrument, \NID1,
	// 	\dur, Pseq([0, k/2], inf)
	// ).play;

});

r.play;

r.reset;

s.boot;

s.quit;