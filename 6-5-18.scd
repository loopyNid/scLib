(
// init
s.boot;
~sample1 = Buffer.read(s, "/home/budos/Music/sample/sample1mono.wav");
x = 0.5;
/// Synth

SynthDef(\heli2,{|freq = 50, vol = 0.6|
	var src, env, envgen;
	env = Env.new([0, 1, 0],[1,1], \sine);
	envgen = EnvGen.kr(env, gate: 1, doneAction: 2);
	src  = LFTri.ar([freq, freq],0,vol*(Pulse.ar(15,0.5)*SinOsc.ar(15)));
	Out.ar(0, (vol)*src*envgen);
}).add;

SynthDef(\Grain,{|impFreq = 2, gDur = 0.5, sndbuf, rate = 1, pos = 0.4 , vol = 0.6|
	var src, env, envgen;
	env = Env.new([0,1],[0.25]);
	envgen = EnvGen.kr(env, gate: 1);
	src = GrainBuf.ar(2, Impulse.ar(impFreq), gDur, sndbuf, rate, pos);
	Out.ar(0, (vol * SinOsc.kr(0.25))*src*envgen);
}).add;

SynthDef(\Grain2,{|impFreq = 2, gDur = 0.5, sndbuf, rate = 1, pos = 0.4 , vol = 0.6|
	var src, env, envgen;
	env = Env.new([0,1],[10]);
	envgen = EnvGen.kr(env, gate: 1);
	src = FreeVerb.ar(GrainBuf.ar(2, Impulse.ar(impFreq), gDur, sndbuf, rate, pos));
	Out.ar(0, (vol)*src*envgen);
}).add;
)
// testing area

Synth(\heli2);

(
SynthDef(\atmo,{|freq = 770 vol = 0.5|
	var src;
	src = 
})
)

//

fork{
	
	a = Pbind(\instrument, \heli2,
		\freq, Pseq([45], inf),
		\dur, 2,
		\vol, 0.7
	);
	
	b = Pbind(\instrument, \Grain,
		\sndbuf, ~sample1,
		\pos, 0.92,
		\gDur, 0.01,
		\dur, 2,
		\rate, 0.5,
		\impFreq, Pseq([45]),
		\vol,  0.5
	);

	c = Pbind(\instrument, \Grain2,
		\sndbuf, ~sample1,
		\pos, 0.43,
		\gDur, 0.01,
		\dur, 10000,
		\impFreq, 777,
	);

	Pdef(\a, a).play;
	Pdef(\b, b).play;
	Pdef(\c, c).play;
	Pdef(\a).stop;
	
}




// add multiple sound files
(
var file, soundPath;
~buffers = List[];
Dialog.getPaths({|paths|
	paths.do({|soundPath|
	soundPath.postln;
	~buffers.add(Buffer.read(s, soundPath););
})
})
)

~buffers[0].play;

Synth(\Grain);

s.quit;