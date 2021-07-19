/* ---- PALETTE ---- */

@water:#175bc9;

@rhein:#175bc9;
@weser:#20d063;
@ems:#0e397d;
@donau:#823f0d;
@elbe:#e7853c;
@oder:#88b0f1;
@weichsel:#1924B0;
@warnowpeene:#23a252;
@traveschlei:#381d99;
@eider:#106932;
@maas:#1ca7f3;
@rhone:#8a3679;
@adria:#c583b8;
@divide:#69b;
				

@font_reg: "DejaVu Sans Book","Arial Regular";
@font_cur: "DejaVu Sans Condensed Oblique","Arial Italic";

/*
Disabled to get transparent background
Map {
  background-color:@water;
}
*/

/* ---- WATER ---- */

/*
#water {
  polygon-fill: @water;
}
*/

#waterways {
  [rsystem='Rhein'] {line-color:@rhein; }
  [rsystem='Weser'] {line-color:@weser; }
  [rsystem='Ems'] {line-color:@ems; }
  [rsystem='Donau'] {line-color:@donau; }
  [rsystem='Elbe'] {line-color:@elbe; }
  [rsystem='Oder'] {line-color:@oder; }
  [rsystem='Weichsel'] {line-color:@weichsel; }
  [rsystem='Warnow'] {line-color:@warnowpeene; }
  [rsystem='Peene'] {line-color:@warnowpeene; }
  [rsystem='Trave'] {line-color:@traveschlei; }
  [rsystem='Schlei'] {line-color:@traveschlei; }
  [rsystem='Eider'] {line-color:@eider; }
  [rsystem='Maas'] {line-color:@maas; }
  [rsystem='Rhone'] {line-color:@rhone; }
  [rsystem='Po'] {line-color:@adria; }
  [rsystem='Etsch'] {line-color:@adria; }
  [rsystem='Isonzo'] {line-color:@adria; }
  [rsystem='Piave'] {line-color:@adria; }
  [rsystem='Tagliamento'] {line-color:@adria; }
  [rsystem='divide'] {line-color:@divide; }
  [rsystem=''] {line-color:gray; }
}

/*
#waterways[type='dam'] {
  line-color:black;
}
*/

#waterways[zoom<11] {
  [zoom<7] {line-width:0.5;}
  [zoom=7] {line-width:0.8;}
  [zoom>=8] {line-width:1;}
}
#waterways[zoom>=11] {
  line-width:2;
  text-face-name:@font_reg;
  text-name:"[name]";
  text-size:10;
  text-placement:line;
  text-fill:spin(darken(@water,36),-10);
  text-halo-fill:rgba(255,255,255,0.8);
  text-halo-radius:2;
  text-max-char-angle-delta:45;
}
#waterways[type='river'] {
  [zoom<10] {line-width:2; text-size:10;}
  [zoom=10] {line-width:4; text-size:12;}
  [zoom>=11] {line-width:6; text-size:14;}
  text-face-name:@font_reg;
  text-name:"[name]";
  text-placement:line;
  text-fill:spin(darken(@water,36),-10);
  text-halo-fill:rgba(255,255,255,0.8);
  text-halo-radius:2;
  text-max-char-angle-delta:45;
}
