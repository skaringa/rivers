/* ---- PALETTE ---- */

@motorway: #F8D6E0;
@trunk: #FFFABB;
@primary: @trunk;
@secondary: @trunk;
@road: #e9db18;
@track: #bbb;
@footway: #bbb;
@cycleway: #69B;

/* ---- LABELS ---- */

.highway-label[type='motorway'] {
  text-face-name:@font_reg;
  text-halo-radius:1;
  text-placement:line;
  text-name:"";
  [zoom>=9] {
    text-size:10;
    text-spacing:100;
    text-name:"[ref]";
    text-fill:spin(darken(@motorway,50),-15);
    text-halo-fill:lighten(@motorway,8);
  }
  [zoom=11] {text-size:12;}
  [zoom=12] {text-size:14;}
}

/* ---- ROAD COLORS ---- */

/* .highway[type='unclassified'] { line-color: @motorway; } /* debug */

.highway[type='motorway'] {
  .line[zoom>=7]  {
    line-color:spin(darken(@motorway,36),-10);
    line-cap:round;
    line-join:round;
  }
  .fill[zoom>=10] {
    line-color:@motorway;
    line-cap:round;
    line-join:round;
  }
}

.highway[type='motorway_link'] {
  .line[zoom>=7]  { 
    line-color:spin(darken(@motorway,36),-10);
    line-cap:round;
    line-join:round;
  }
  .fill[zoom>=12] {
    line-color:@motorway;
    line-cap:round;
    line-join:round;
  }
}

.highway[type='trunk'],
.highway[type='trunk_link'] {
  .line[zoom>=7] {
    line-color:spin(darken(@trunk,36),-10);
    line-cap:round;
    line-join:round;
  }
  .fill[zoom>=11] {
    line-color:@trunk;
    line-cap:round;
    line-join:round;
  }
}

.highway[type='primary'],
.highway[type='primary_link'] {
  .line[zoom>=7] {
    line-color:spin(darken(@primary,36),-10);
    line-cap:round;
    line-join:round;
  }
  .fill[zoom>=12] {
    line-color:@primary;
    line-cap:round;
    line-join:round;
  }
}

.highway[type='secondary'] {
  .line[zoom>=8] {
    line-color:spin(darken(@secondary,36),-10);
    line-cap:round;
    line-join:round;
  }
  .fill[zoom>=12] {
    line-color:@secondary;
    line-cap:round;
    line-join:round;
  }
}

.highway[type='secondary_link'] {
  .line[zoom>=12] {
    line-color:spin(darken(@secondary,36),-10);
    line-cap:round;
    line-join:round;
  }
  .fill[zoom>=14] {
    line-color:@secondary;
    line-cap:round;
    line-join:round;
  }
}

.highway[type='living_street'],
.highway[type='residential'],
.highway[type='road'],
.highway[type='tertiary'],
.highway[type='unclassified'],
.highway[type='service'] {
  .line[zoom>=10] {
    line-color:@road;
    line-cap:round;
    line-join:round;
  }
  .fill[zoom>=13] {
    line-color:#fff;
    line-cap:round;
    line-join:round;
  }
}



.highway[type='track'] {
  .line[zoom>=13] {
    line-color:@track;
    line-cap:round;
    line-join:round;
  }
}

.highway[type='footway'],
.highway[type='path'],
.highway[type='pedestrian'] {
  .line[zoom>=13] {
    line-color:@footway;
    line-cap:round;
    line-join:round;
  }
}

.highway[type='cycleway'] {
  .line[zoom>=14] {
    line-color:@cycleway;
    line-cap:round;
    line-join:round;
  }
}

/* ---- ROAD WIDTHS ---- */

.highway[zoom=7] {
  .line[type='motorway'] { line-width: 2.0; }
  .line[type='trunk']    { line-width: 1.8; }
  .line[type='primary']  { line-width: 1.6; }
}

.highway[zoom=8] {
  .line[type='motorway'] { line-width: 2.0; }
  .line[type='trunk']    { line-width: 1.8; }
  .line[type='primary']  { line-width: 1.5; }
  .line[type='secondary']{ line-width: 1.3; }
}

.highway[zoom=9] {
  .line[type='motorway'] { line-width: 2.0; }
  .line[type='trunk']    { line-width: 1.8; }
  .line[type='primary']  { line-width: 1.6; }
  .line[type='secondary']{ line-width: 1.4; }
}

.highway[zoom=10] {
  .line[type='motorway'] { line-width: 1.8 + 2.6; }
  .fill[type='motorway'] { line-width: 1.8; }
  
  .line[type='trunk']    { line-width: 2.4; }
  
  .line[type='primary']  { line-width: 2.2; }
  
  .line[type='secondary']{ line-width: 1.8; }

}

.highway[zoom=11] {
  .line[type='motorway']      { line-width: 2.0 + 2.8; }
  .fill[type='motorway']      { line-width: 2.0; }
  .line[type='trunk']         { line-width: 1.8 + 2.6; }
  .fill[type='trunk']         { line-width: 1.8; }
  .line[type='primary']       { line-width: 2.4; }
  .line[type='secondary']     { line-width: 2.0; }
  
  .line[type='motorway_link'] { line-width: 1.6; }
  .line[type='trunk_link']    { line-width: 1.5; }
  .line[type='primary_link']  { line-width: 1.4; }
}

.highway[zoom=12] {
  .line[type='motorway']      { line-width: 2.5 + 4; }
  .fill[type='motorway']      { line-width: 2.5; }
  .line[type='trunk']         { line-width: 2.2 + 3.8; }
  .fill[type='trunk']         { line-width: 2.2; }
  .line[type='primary']       { line-width: 2.0 + 2.6; }
  .fill[type='primary']       { line-width: 2.0; }
  .line[type='secondary']     { line-width: 2.0 + 2.6; }
  .fill[type='secondary']     { line-width: 2.0; }
  
  .line[type='motorway_link'] { line-width: 2.5 + 2.8; }
  .fill[type='motorway_link'] { line-width: 2.5; }
  .line[type='trunk_link']    { line-width: 2.2 + 2.6; }
  .fill[type='trunk_link']    { line-width: 2.2; }
  .line[type='primary_link']  { line-width: 2.0 + 2.6; }
  .fill[type='primary_link']  { line-width: 2.0; }
  .line[type='secondary_link']  { line-width: 2.0; }
  
  .line[type='living_street'],
  .line[type='residential'],
  .line[type='road'],
  .line[type='tertiary'],
  .line[type='unclassified'],
  .line[type='service']       { line-width: 1.8; }

} 

.highway[zoom=13] {
  .line[type='motorway']      { line-width: 3.8 + 4; }
  .fill[type='motorway']      { line-width: 3.8; }
  .line[type='trunk']         { line-width: 3.8 + 4; }
  .fill[type='trunk']         { line-width: 3.8; }
  .line[type='primary']       { line-width: 3.6 + 4; }
  .fill[type='primary']       { line-width: 3.6; }
  .line[type='primary_link'],
  .line[type='secondary']     { line-width: 3.4 + 3; }
  .fill[type='primary_link'],
  .fill[type='secondary']     { line-width: 3.4; }
  
  .line[type='motorway_link'] { line-width: 3.8 + 3; }
  .fill[type='motorway_link'] { line-width: 3.8; }
  .line[type='trunk_link']    { line-width: 3.8 + 3; }
  .fill[type='trunk_link']    { line-width: 3.8; }
  .line[type='primary_link']  { line-width: 2.8 + 3; }
  .fill[type='primary_link']  { line-width: 2.8; }
  .line[type='secondary_link']{ line-width: 2.6; }
  
  .line[type='living_street'],
  .line[type='residential'],
  .line[type='road'],
  .line[type='tertiary'],
  .line[type='unclassified'],
  .line[type='service']       { line-width: 2.6 + 2; }
  .fill[type='living_street'],
  .fill[type='residential'],
  .fill[type='road'],
  .fill[type='tertiary'],
  .fill[type='unclassified'],
  .fill[type='service']       { line-width: 2.6; }

  .line[type='track']         { line-width: 1.8;}
  .line[type='path']         { line-width: 1.8; line-dasharray:2,3;}
}

.highway[zoom=14] {
  .line[type='motorway']      { line-width: 5 + 3; }
  .fill[type='motorway']      { line-width: 5; }
  .line[type='trunk']         { line-width: 4.5 + 3; }
  .fill[type='trunk']         { line-width: 4.5; }
  .line[type='primary']       { line-width: 4 + 3; }
  .fill[type='primary']       { line-width: 4; }
  .line[type='secondary']     { line-width: 3.8 + 3; }
  .fill[type='secondary']     { line-width: 3.8; }
  
  .line[type='motorway_link'] { line-width: 4.8 + 3; }
  .fill[type='motorway_link'] { line-width: 4.8; }
  .line[type='trunk_link']    { line-width: 4 + 3; }
  .fill[type='trunk_link']    { line-width: 4; }
  .line[type='primary_link']  { line-width: 3.8 + 3; }
  .fill[type='primary_link']  { line-width: 3.8; }
  .line[type='secondary_link']{ line-width: 3.6 + 2; }
  .fill[type='secondary_link']{ line-width: 3.6; }
  
  .line[type='living_street'],
  .line[type='residential'],
  .line[type='road'],
  .line[type='tertiary'],
  .line[type='unclassified']  { line-width: 3.0 + 2.6; }
  .fill[type='living_street'],
  .fill[type='residential'],
  .fill[type='road'],
  .fill[type='tertiary'],
  .fill[type='unclassified']  { line-width: 3.0; }
  .line[type='service']       { line-width: 3.0; }
  
  .line[type='track']         { line-width: 2.0;}
  
  .line[type='cycleway'],
  .line[type='footway'],
  .line[type='path'],
  .line[type='pedestrian'] {
    line-dasharray:2,3;
    line-width:2.0;
  }
}

.highway[zoom>=15] {
  .line[type='motorway']      { line-width: 8 + 2; }
  .fill[type='motorway']      { line-width: 8; }
  .line[type='trunk']         { line-width: 7 + 2; }
  .fill[type='trunk']         { line-width: 7; }
  .line[type='primary']       { line-width: 6.5 + 2; }
  .fill[type='primary']       { line-width: 6.5; }
  .line[type='secondary']     { line-width: 6 + 2; }
  .fill[type='secondary']     { line-width: 6; }
  
  .line[type='motorway_link'] { line-width: 7 + 2; }
  .fill[type='motorway_link'] { line-width: 7; }
  .line[type='trunk_link']    { line-width: 6.5 + 2; }
  .fill[type='trunk_link']    { line-width: 6.5; }
  .line[type='primary_link']  { line-width: 6 + 2; }
  .fill[type='primary_link']  { line-width: 6; }
  .line[type='secondary_link']{ line-width: 5.5 + 2; }
  .fill[type='secondary_link']{ line-width: 5.5; }
  
  .line[type='living_street'],
  .line[type='residential'],
  .line[type='road'],
  .line[type='tertiary'],
  .line[type='unclassified']  { line-width: 5 + 2; }
  .fill[type='living_street'],
  .fill[type='residential'],
  .fill[type='road'],
  .fill[type='tertiary'],
  .fill[type='unclassified']  { line-width: 5; }
  .line[type='service']       { line-width: 4; }
  
  .line[type='track']         { line-width: 2.5;}
  
  .line[type='cycleway'],
  .line[type='footway'],
  .line[type='path'],
  .line[type='pedestrian'] {
     line-dasharray:2,3;
    line-width:2.5;
  }
}
