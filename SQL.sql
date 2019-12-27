
-- ---
-- Table 'imagenes'
-- 
-- ---

DROP TABLE IF EXISTS `imagenes`;
		
CREATE TABLE `imagenes` (
  `id_imagen` INT NOT NULL AUTO_INCREMENT,
  `imagen` MEDIUMBLOB NOT NULL,
  `imagen_checksum` BINARY NOT NULL,
  `id_usuario_subido` SMALLINT NOT NULL,
  `fecha` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id_imagen`),
KEY (`id_usuario_subido`)
);

-- ---
-- Table 'usuarios'
-- 
-- ---

DROP TABLE IF EXISTS `usuarios`;
		
CREATE TABLE `usuarios` (
  `id_usuario` SMALLINT NOT NULL AUTO_INCREMENT,
  `usuario` VARCHAR(30) NOT NULL DEFAULT 'UNIQUE',
  `clave` VARCHAR(30) NOT NULL DEFAULT 'UNIQUE',
  PRIMARY KEY (`id_usuario`)
);

-- ---
-- Table 'galerias'
-- 
-- ---

DROP TABLE IF EXISTS `galerias`;
		
CREATE TABLE `galerias` (
  `id_galeria` SMALLINT NOT NULL AUTO_INCREMENT,
  `id_usuario` SMALLINT NOT NULL,
  `fecha` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id_galeria`),
KEY (`id_usuario`)
);

-- ---
-- Table 'galerias_imagenes'
-- 
-- ---

DROP TABLE IF EXISTS `galerias_imagenes`;
		
CREATE TABLE `galerias_imagenes` (
  `id_imagen` INT NOT NULL,
  `id_galeria` SMALLINT NOT NULL,
  PRIMARY KEY (`id_galeria`, `id_imagen`)
);

-- ---
-- Table 'noticias'
-- 
-- ---

DROP TABLE IF EXISTS `noticias`;
		
CREATE TABLE `noticias` (
  `id_noticia` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(128) NOT NULL DEFAULT 'UNIQUE',
  `cuerpo` MEDIUMTEXT NOT NULL,
  `imagen_relevante` INT NOT NULL,
  `id_usuario` SMALLINT NOT NULL,
  PRIMARY KEY (`id_noticia`),
KEY (`id_usuario`, `imagen_relevante`)
);

-- ---
-- Table 'noticias_imagenes'
-- 
-- ---

DROP TABLE IF EXISTS `noticias_imagenes`;
		
CREATE TABLE `noticias_imagenes` (
  `id_noticia` INT NOT NULL,
  `id_imagen` INT NOT NULL,
  PRIMARY KEY (`id_noticia`, `id_imagen`)
);

-- ---
-- Table 'tags'
-- 
-- ---

DROP TABLE IF EXISTS `tags`;
		
CREATE TABLE `tags` (
  `id_tag` MEDIUMINT NOT NULL AUTO_INCREMENT,
  `tag` VARCHAR(64) NOT NULL DEFAULT 'UNIQUE',
  PRIMARY KEY (`id_tag`)
);

-- ---
-- Table 'noticas_tags'
-- 
-- ---

DROP TABLE IF EXISTS `noticas_tags`;
		
CREATE TABLE `noticas_tags` (
  `id_noticia` INT NOT NULL,
  `id_tag` MEDIUMINT NOT NULL,
  PRIMARY KEY (`id_noticia`, `id_tag`)
);

-- ---
-- Foreign Keys 
-- ---

ALTER TABLE `imagenes` ADD FOREIGN KEY (id_usuario_subido) REFERENCES `usuarios` (`id_usuario`);
ALTER TABLE `galerias` ADD FOREIGN KEY (id_usuario) REFERENCES `usuarios` (`id_usuario`);
ALTER TABLE `galerias_imagenes` ADD FOREIGN KEY (id_imagen) REFERENCES `imagenes` (`id_imagen`);
ALTER TABLE `galerias_imagenes` ADD FOREIGN KEY (id_galeria) REFERENCES `galerias` (`id_galeria`);
ALTER TABLE `noticias` ADD FOREIGN KEY (imagen_relevante) REFERENCES `imagenes` (`id_imagen`);
ALTER TABLE `noticias` ADD FOREIGN KEY (id_usuario) REFERENCES `usuarios` (`id_usuario`);
ALTER TABLE `noticias_imagenes` ADD FOREIGN KEY (id_noticia) REFERENCES `noticias` (`id_noticia`);
ALTER TABLE `noticias_imagenes` ADD FOREIGN KEY (id_imagen) REFERENCES `imagenes` (`id_imagen`);
ALTER TABLE `noticas_tags` ADD FOREIGN KEY (id_noticia) REFERENCES `noticias` (`id_noticia`);
ALTER TABLE `noticas_tags` ADD FOREIGN KEY (id_tag) REFERENCES `tags` (`id_tag`);
