s.boot;
s.quit;

(
SynthDef(\NID, {arg freq = 1289, vol = 0.6;
	var snd;
	var env;
	var envgen;
	env = Env.perc(0.01, 1, 1, -4);
	envgen = EnvGen.kr(env, gate: 1, doneAction: 2);
	snd = SinOsc.ar(freq, 0, envgen * vol) + LFTri.ar(freq, 0, envgen * vol);
	Out.ar([1, -1], snd)
	
}).add;
)

Synth(\NID)


(
SynthDef(\NID1, {arg freq = 45, vol = 0.6;
	var snd;
	var env;
	var envgen;
	env = Env.perc(0.01, 3, 1, -4);
	envgen = EnvGen.kr(env, gate: 1, doneAction: 2);
	snd = LFTri.ar(freq, 0, envgen * vol);
	Out.ar([1, -1], snd)
}).add;
)

r.reset;

r.play;
(
r = Routine({
	
	j = Scale.mixolydian;

	//- πως αλλαζω κλημακα ανα συνγκεκριμενο χρονικο διαστημα
	// t = Task({ {
	// 	5.wait;
	// 	if( j == Scale.mixolydian,{
	// 		j = Scale.phrygian;
	// 	});
		
	// 	5.wait
		
	// 	if( j == Scale.phrygian,{
	// 		j = Scale.mixolydian;
	// 	});		


	// }.loop }).start;
	
	Pbind(\instrument, \NID,
		\degree, Prand([0, 1, 3, 5, 7, 9], inf),
		\scale, Pfunc({ j }, inf),
		\root, 5,
		\dur, 0.1,
		\vol, 0.2
	).play;
	
	Pbind(\instrument, \NID1,
		\freq, Pseq([Pseq([44])], inf),
		\dur, 2,
		\vol, 0.6
	).play;

});
)
r.play;

r.reset;
FreqScope.new;
