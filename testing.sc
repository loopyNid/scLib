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






