/* ---- PALETTE ---- */

@water:#175bc9;

@rhein:#175bc9;
@weser:#20d063;
@ems:#0e397d;
@donau:#823f0d;
@elbe:#e7853c;
@oder:#88b0f1;
@warnowpeene:#23a252;
@traveschlei:#381d99;
@eider:#106932;
@maas:#44e7a0;
@rhone:#8a3679;
@divide:#69b;
				

@forest:#cea;
@land:#fff;
@residential:#dddcdc;

@font_reg: "Ubuntu Regular","Arial Regular","DejaVu Sans Book";
@font_cur: "Arial Italic","DejaVu Sans Italic";

Map {
  background-color:@water;
}

/* See the wiki for info:
<http://wiki.openstreetmap.org/wiki/Mapnik#World_boundaries> 
#shoreline_300[zoom<11],
#processed_p[zoom>=11] {
  polygon-fill: @land;
}
*/
/* See http://openstreetmapdata.com/data/land-polygons */
#landpolygons {
  polygon-fill: @land;
  polygon-gamma: 0.2;
}

/* ----- RELIEF ----- */
#color-relief,
#slope-shade,
#hill-shade {
    raster-scaling: bilinear;
    // note: in TileMill 0.9.x and earlier this is called raster-mode
    raster-comp-op: multiply;
}

#hill-shade { raster-opacity: 0.2; }
#slope-shade { raster-opacity: 0.4; }
#color-relief { raster-opacity: 0.5; }

/* ---- WATER ---- */

#water {
  polygon-fill: @water;
}

#waterways {
  [rsystem='Rhein'] {line-color:@rhein; }
  [rsystem='Weser'] {line-color:@weser; }
  [rsystem='Ems'] {line-color:@ems; }
  [rsystem='Donau'] {line-color:@donau; }
  [rsystem='Elbe'] {line-color:@elbe; }
  [rsystem='Oder'] {line-color:@oder; }
  [rsystem='Warnow'] {line-color:@warnowpeene; }
  [rsystem='Peene'] {line-color:@warnowpeene; }
  [rsystem='Trave'] {line-color:@traveschlei; }
  [rsystem='Schlei'] {line-color:@traveschlei; }
  [rsystem='Eider'] {line-color:@eider; }
  [rsystem='Maas'] {line-color:@maas; }
  [rsystem='Rhone'] {line-color:@rhone; }
  [rsystem='divide'] {line-color:@divide; }
  [rsystem=''] {line-color:gray; }
}

#waterways[type='dam'] {
  line-color:black;
}

#waterways[zoom<11] {
  line-width:1;
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
}
#waterways[type='river'][zoom<11] {
  line-width:2;
  text-face-name:@font_reg;
  text-name:"[name]";
  text-size:10;
  text-placement:line;
  text-fill:spin(darken(@water,36),-10);
  text-halo-fill:rgba(255,255,255,0.8);
  text-halo-radius:2;
}
#waterways[type='river'][zoom>=11] {
  line-width:6;
  text-face-name:@font_reg;
  text-name:"[name]";
  text-size:14;
  text-placement:line;
  text-fill:spin(darken(@water,36),-10);
  text-halo-fill:rgba(255,255,255,0.8);
  text-halo-radius:2;
}


/* ---- LOCATION ---- */
.location [type='city'][zoom>6] {
  text-face-name:@font_reg;
  text-name:"[name]";
  text-fill:#222;
  text-halo-fill:rgba(255,255,255,0.2);
  text-halo-radius:2;
  text-allow-overlap:true;
  text-dy:-8;
  marker-type:ellipse;
  marker-fill:red;
  marker-opacity:0.8;
  marker-line-color:black;
  marker-allow-overlap:false;
  [zoom=7] {text-size:10;}
  [zoom=8] {text-size:12;}
  [zoom>=9] {text-size:14;}
}

.location [type='town'][zoom>=10] {
  text-face-name:@font_reg;
  text-name:"[name]";
  text-fill:#444;
  text-halo-fill:rgba(255,255,255,0.2);
  text-halo-radius:2;
  text-allow-overlap:true;
  text-size:14;
  text-dy:-8;
  marker-type:ellipse;
  marker-fill:gray;
  marker-opacity:0.8;
  marker-line-color:black;
  marker-allow-overlap:false;
}

/* --- BORDER --- */
.border[level=2] {
  line-color:rgba(255,0,0,0.4);
  line-join:round;
  [zoom=6] {line-width:1; line-dasharray:4,2; }
  [zoom=7] {line-width:1.5; line-dasharray:4,2; }
  [zoom=8] {line-width:2; line-dasharray:4,4,2,2; }
  [zoom>=9] {line-width:3; line-dasharray:6,6,3,3; }
}
