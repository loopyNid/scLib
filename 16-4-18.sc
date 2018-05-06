(
SynthDef(\NID,{arg freq = 1289, freqK = 10, vol=0.6;
	var src;
	var env;
	var envgen;
	env = Env.perc(0.001, 2, 0.6, -4);
	envgen = EnvGen.kr(env, gate:1, doneAction:2);
	src = BrownNoise.ar([1,1]) * LFTri.ar(freq, 0, envgen * vol);
	Out.ar([1, -1],src)
}).add;
)

(
SynthDef(\NID1,{arg freq = 1289, freqK = 10, vol=0.6;
	var src;
	var env;
	var envgen;
	env = Env.perc(0.001, 1, 0.6, -4);
	envgen = EnvGen.kr(env, gate:1, doneAction:2);
	src = Blip.ar([WhiteNoise.kr(10)*freqK, WhiteNoise.kr(10)*(SinOsc.ar(50) * freqK)], 200, vol * envgen);
	Out.ar([1, -1],src)
}).add;
)

Synth(\NID1)





(
fork{
	
	~degreP = Prand((1..13), inf);
	
	play{LFTri.ar([44, 44], 0, SinOsc.kr(0.01))};

	j = Scale.mixolydian;
	
	10.wait;
	
	~nid1 = Pbind(
		\instrument, \NID1,
		\freqK, Prand([5, 7, 10, 35, 50, 100, 200], inf),
		\dur, 0.1,
		\vol, 0.3
	).play;

	10.wait;
	
	Pdef(~nid1, Pbind(\freqK, 50)); 
	// 10.wait;
	
	// ~nid = Pbind(
	// 	\instrument, \NID,
	// 	\degree, ~degreP,
	// 	\scale, Pfunc({ j }, inf),
	// 	\root, 5,
	// 	\vol, 0.3,
	// 	\dur, Prand([0.1, 0.2], inf)
	// ).play;

	// 3.wait;

	// j = Scale.phrygian;

	// 5.wait;

	// j = Scale.mixolydian;

	// 5.wait;

	// j = Scale.phrygian;
}
)

	
	
play{Blip.ar([WhiteNoise.kr(10)*5, WhiteNoise.kr(10)*(SinOsc.ar(50) * 10)])};


s.boot;
s.quit;

Pdef(~nid1, Pbind(\vol, 1));

(
fork{

~scaleS = Scale.bartok;//experiment with other scales as well. See Scale.directory


~pattP = Prand((0..17)++ [\rest] ++ (16..0)++[\rest], inf);

~pattD = Pn(Pgeom(0.25, 1, inf), Pgeom(1, 0.25, inf), inf);

~pattD2 = Pn(Prand([0.025, 0.34, 0.16, 1], inf), Pseq([1, 0.34, 0.56, 0.25], inf), inf);

Pdef(\first_mov1,
 Pbind(\scale, ~scaleS,
	\degree, ~pattP,
	\dur, ~pattD)).play;

	"start with Bartok scale".postln;

	15.wait;

	Pdef(\first_mov1).stop;

	"change to Dorian".postln;

~scaleS = Scale.dorian;

Pdef(\first_mov2,
 Pbind(\scale, ~scaleS,
	\degree, ~pattP,
	\dur, ~pattD2)).play;

	14.wait;
	"now both".postln;

	Pdef(\first_mov1).play;

	3.wait;
        "8s to close".postln;

	8.wait;// change this to a bigger number in case you want more

	Pdef(\first_mov1).stop;
	Pdef(\first_mov2).stop;

	"thats it".postln;
};
)