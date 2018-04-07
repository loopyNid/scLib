play{Blip.ar([WhiteNoise.kr(10)*5, WhiteNoise.kr(10)*(SinOsc.ar(50) * 10)])};

(
SynthDef("NID",{arg freq= 60, vol=0.6;
	//envelope
	var env;
	var src;
	var envgen;
	env = Env.perc(0.001, 1, 1, -4);
	envgen = EnvGen.kr(env, gate:1, doneAction:2);
	src = SinOsc.ar([freq, freq],0,vol*envgen);
	Out.ar(0,src)
}).add;
)

(
SynthDef("NID1",{arg vol = 0.7;
	//envelope
	var env;
	var src;
	var envgen;
	env = Env.perc(0.001, 1, 0.6, -4);
	envgen = EnvGen.kr(env, gate:1, doneAction:2);
	src = WhiteNoise.ar([vol*envgen, vol*envgen]);
	Out.ar(0,src)
}).add;
)

Pbind(
	\instrument, \NID,
	\freq, Pseq([60, 55, 50, 45], inf),
	\vol, 1,
	\dur, 0.7
).play;


Pbind(
	\instrument, \NID1,
	\vol, 0.5,
	\dur, 0.7
).play;

