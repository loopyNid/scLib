(
SynthDef(\dust,{arg freq = 444, vol = 0.6, freqS = 1, phaseDif = pi/2;
	var duster;
	duster = [Dust.ar(freq) * SinOsc.kr(freqS), Dust.ar(freq) * SinOsc.kr(freqS,pi/4) ];
	Out.ar(0, duster*vol);
}).add;
)

e = (type: \noise,
	instrument: \dust,
	freq: 9000,
	freqS: 13,
	phaseDif: pi/2
);


e.play;