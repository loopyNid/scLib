/*
BA 181602
Composing music with patterns paradigms.
Objective: a) Experiment with patterns and scales b) Change scale and duration after n time, c) play all scales, d) stop the music
*/

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






/*
BA 180205
Create data and collect
Objective: Create data (an array of values i.e integers or floats), and map them to the arguments of the synth
*/

//Create and collect data as stream (array)
//iterate over a collection
(
a = 10.do{|n|
    n.postln;
};
)
//collect items between 0-9 
k = a.collect({ |item| item.rand.asStream });
// play the collection in Event pattern
Pbind(\dur, 0.09, \degree, Pseq(k)).play;



//with Array.fill

 s.waitForBoot{

fork{
//create a synthdef
b = Buffer.read(s, Platform.resourceDir +/+ "sounds/a11wlk01.wav");

1.wait;

SynthDef(\tgrain, {| gate = 1, trate = 1, dur = 1, rate = 1, bufdur = 1, amp = 0.5 |

	var source, env;

	env = EnvGen.kr( Env.asr, gate, doneAction: 2);

source = TGrains.ar(2, Impulse.ar(trate), b, rate, BufDur.kr(b)*bufdur, dur, Dseq([-1, 1], inf), 0.1, 2);


	Out.ar(0, source * env * amp);
}).add;


0.5.wait;

// run the synth
//x = Synth(\tgrain);

0.5.wait;

// create an Array of random values and set them to the synth

	e = Array.fill(100, { rrand(0, 20)});
	e.postln;



Pbind(\instrument, \tgrain, \dur, Pseq(e.sqrt), \trate, Pseq(e), \rate, 1, \amp, 1, \bufdur, Pseq(e.reciprocal)).play;


};
}

