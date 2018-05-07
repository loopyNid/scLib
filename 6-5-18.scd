(
// init
s.boot;
~sample1 = Buffer.read(s, "/home/budos/Music/samples/metallosamplemono.wav");
~sample1.play;
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
	env = Env.new([0,1,0],[1,1]);
	envgen = EnvGen.kr(env, gate: 1, doneAction: 2);
	src = GrainBuf.ar(2, Impulse.ar(impFreq), gDur, sndbuf, rate, pos);
	Out.ar(0, (vol * SinOsc.kr(0.25))*src*envgen);
}).add;

SynthDef(\Grain2,{|impFreq = 2, gDur = 0.5, sndbuf, rate = 1, pos = 0.4 , vol = 0.6|
	var src, env, envgen;
	env = Env.new([0,1,0],[1,1]);
	envgen = EnvGen.kr(env, gate: 1, doneAction: 2);
	src = FreeVerb.ar(GrainBuf.ar(2, Impulse.ar(impFreq), gDur, sndbuf, rate, pos), mix: 0.33, room: 0.5, damp: 0.5, );
	Out.ar(0, (vol)*src*envgen);
}).add;

)

// testing area
Synth(\Grain);
play{SinOsc.ar()}


//

fork{
	
	// a = Pbind(\instrument, \heli2,
	// 	\freq, 45
	// 	\dur, 2,
	// 	\vol, 0.5
	// );
	
	b = Pbind(\instrument, \Grain,
		\sndbuf, ~sample1,
		\pos, 0.02,
		\gDur, 0.2,
		\dur, 2,
		\rate, 0.5,
		\impFreq, Pseq([45, 45, 45, 45], inf),
		\vol, 0.5
	);

	c = Pbind(\instrument, \Grain2,
		\sndbuf, ~sample1,
		\pos, 0.92,
		\gDur, 0.01,
		\dur, 0.1,
		\impFreq, 45,
	);

	Pdef(\c, c).play;
	8.wait;
	Pdef(\b, b).play;
	
	
}

o = ServerOptions.new;
o.memSize = 16400;
o.memSize


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