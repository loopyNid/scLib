(
// init
s.boot;
~sample1 = Buffer.read(s, File.getcwd +/+ "samples/metallosamplemono.wav");
~sample2 = Buffer.read(s, File.getcwd +/+ "samples/scatemono.wav");
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
	src = FreeVerb.ar(GrainBuf.ar(2, Impulse.ar(impFreq), gDur, sndbuf, rate, pos), mix: 0.33, room: 1, damp: 0.5, );
	Out.ar(0, vol*src*envgen);
}).add;
SynthDef(\klk, {|impFreq = 2, gDur = 0.5, sndbuf, rate = 1, pos = 0.4 , vol = 0.6|
	var src, env, envgen;
	env = Env.perc();
	envgen = EnvGen.kr(env, gate:1, doneAction: 2);
	src = GrainBuf.ar(2, Impulse.ar(impFreq), gDur, sndbuf, rate, pos);
	Out.ar(0, vol*src*envgen);
}).add;
SynthDef(\b4f, {|freq = 45, vol = 0.7|
	var src, envgen;
	envgen = EnvGen.kr(Env.perc());
	src = Pan2.ar(SinOsc.ar(freq));
	Out.ar(0, src*envgen*vol);
}).add;
)
(
fork{
	a = Pbind(\instrument, \heli2,
		\freq, 45,
		\dur, 2,
		\vol, 0.5
	);
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
		\vol, 2
	);
	~klk = Pbind(\instrument, \klk,
		\sndbuf, ~sample2,
		\pos, 0.94,
		\gDur, 0.1,
		\dur, Prand([0.1, 0.5, 1, 2, 4],  inf),
		\rate, 1,
		\impFreq, 20,
		\vol, 2
	);
	~b4f = Pbind(\instrument, \b4f,
		\freq, 45,
		\dur, 0.5,
		\vol, 0.1
	);
	Pdef(\klk, ~klk).play;
	5.do({
		Pdef(\c, c).play;
		Pdef(\b, b).play;
		Pdef(\d).pause;
		5.wait;
		Pdef(\c, Pbind(\instrument, \Grain2,
			\sndbuf, ~sample1,
			\pos, 0.92,
			\gDur, 0.01,
			\dur, 0.1,
			\impFreq, 60,
			\vol, 2
		)).play;
		5.wait;
	});
	Pdef(\a, a).play;
	Pdef(\b4f, ~b4f).play;
	Pdef(\klk, Pbind(\instrument, \klk,
		\sndbuf, ~sample2,
		\pos, 0.94,
		\gDur, 0.1,
		\dur, 0.5,
		\rate, 1,
		\impFreq, 20,
		\vol, Pseq([0, 2, 0 ,2], inf)
	)).play;
	Pdef(\d, Pbind(\instrument, \Grain2,
		\sndbuf, ~sample1,
		\pos, 0.02,
		\gDur, 0.01,
		\dur, 0.1,
		\impFreq, 45,
		\vol, 0.01
	)).play;
}
)
// server memsize code has memory problem
s.options.memSize = 16400;

s.quit;
