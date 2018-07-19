/*
MySQL Backup
Database: fast-admin
Backup Time: 2018-07-19 09:37:34
*/

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `fast-admin`.`sys_dic`;
DROP TABLE IF EXISTS `fast-admin`.`sys_menu`;
DROP TABLE IF EXISTS `fast-admin`.`sys_org`;
DROP TABLE IF EXISTS `fast-admin`.`sys_role`;
DROP TABLE IF EXISTS `fast-admin`.`sys_role_menu`;
DROP TABLE IF EXISTS `fast-admin`.`sys_role_user`;
DROP TABLE IF EXISTS `fast-admin`.`sys_user`;
DROP FUNCTION IF EXISTS `fast-admin`.`getDicText`;
DROP FUNCTION IF EXISTS `fast-admin`.`getOrgName`;
CREATE TABLE `sys_dic` (
  `ID` varchar(50) NOT NULL COMMENT '字典表编号',
  `DICDEFINE` varchar(200) DEFAULT NULL COMMENT '字典定义',
  `DICDESC` varchar(200) DEFAULT NULL COMMENT '字典描述',
  `DICCODE` varchar(20) DEFAULT NULL COMMENT '字典编码',
  `DICNAME` varchar(200) DEFAULT NULL COMMENT '字典名称',
  `ISUSE` varchar(20) DEFAULT NULL COMMENT '使用状态',
  `CRT_USER` varchar(200) DEFAULT NULL COMMENT '创建人',
  `CRT_TIME` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `sys_menu` (
  `ID` varchar(50) NOT NULL COMMENT 'ID',
  `MENU_ID` varchar(20) NOT NULL DEFAULT '' COMMENT '菜单ID',
  `MENU_NAME` varchar(200) NOT NULL COMMENT '菜单名称',
  `MENU_PID` varchar(20) DEFAULT NULL COMMENT '菜单父级ID',
  `MENU_URL` varchar(400) DEFAULT NULL COMMENT '菜单地址',
  `MENU_PARM` varchar(50) DEFAULT NULL COMMENT '权限值',
  `MENU_TYPE` varchar(50) DEFAULT NULL COMMENT '菜单类型',
  `MENU_ICON` varchar(50) DEFAULT NULL COMMENT '菜单图片',
  `MENU_SORT` varchar(20) DEFAULT NULL COMMENT '菜单排序',
  `CREATE_TIME` date DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_ORG` varchar(20) DEFAULT NULL COMMENT '创建单位',
  `UPDATE_TIME` date DEFAULT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_ORG` varchar(20) DEFAULT NULL COMMENT '修改单位',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `sys_org` (
  `ID` varchar(50) NOT NULL COMMENT 'ID',
  `ORG_CODE` varchar(20) NOT NULL COMMENT '单位编码',
  `ORG_NAME` varchar(100) NOT NULL COMMENT '单位名称',
  `ORG_PID` varchar(20) DEFAULT NULL COMMENT '父ID',
  `STATUE` varchar(6) DEFAULT NULL COMMENT '使用状态',
  `MEMO` varchar(1000) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` date DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_ORG` varchar(20) DEFAULT NULL COMMENT '创建单位',
  `UPDATE_TIME` date DEFAULT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_ORG` varchar(20) DEFAULT NULL COMMENT '修改单位',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `sys_role` (
  `ROLE_ID` varchar(50) NOT NULL COMMENT '角色ID',
  `ROLE_NAME` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `ROLE_CODE` varchar(20) DEFAULT NULL COMMENT '角色编码',
  `ROLE_STATUS` varchar(20) DEFAULT NULL COMMENT '使用状态',
  `CREATE_TIME` date DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_ORG` varchar(20) DEFAULT NULL COMMENT '创建单位',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `sys_role_menu` (
  `ID` varchar(50) NOT NULL COMMENT 'ID',
  `ROLE_ID` varchar(50) NOT NULL COMMENT '角色ID',
  `MENU_ID` varchar(50) NOT NULL COMMENT '菜单ID',
  `STATUES` varchar(6) DEFAULT NULL COMMENT '使用状态',
  `CREATE_TIME` date DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_ORG` varchar(20) DEFAULT NULL COMMENT '创建单位',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `sys_role_user` (
  `ID` varchar(50) NOT NULL COMMENT 'ID',
  `ROLE_ID` varchar(50) NOT NULL COMMENT '角色ID',
  `USER_ID` varchar(50) NOT NULL COMMENT '用户ID',
  `STATUES` varchar(6) DEFAULT NULL COMMENT '状态',
  `CREATE_TIME` date DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_ORG` varchar(20) DEFAULT NULL COMMENT '创建单位',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `sys_user` (
  `ID` varchar(50) NOT NULL COMMENT 'ID',
  `LOGINID` varchar(20) NOT NULL COMMENT '登录ID',
  `USERNAME` varchar(50) DEFAULT NULL COMMENT '用户名',
  `PASSWORD` varchar(50) DEFAULT NULL COMMENT '密码',
  `SALT` varchar(50) DEFAULT NULL COMMENT '密码盐',
  `ORGID` varchar(20) DEFAULT NULL COMMENT '单位ID',
  `TELPHONE` varchar(50) DEFAULT NULL COMMENT '电话',
  `EMAIL` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `ADDR` varchar(200) DEFAULT NULL COMMENT '地址',
  `STATUE` varchar(6) DEFAULT NULL COMMENT '状态',
  `MEMO` varchar(1000) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` date DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_ORG` varchar(20) DEFAULT NULL COMMENT '创建单位',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE DEFINER=`root`@`localhost` FUNCTION `getDicText`(define VARCHAR(255),code VARCHAR(255)) RETURNS varchar(255) CHARSET utf8
    DETERMINISTIC
begin  
        DECLARE dicText VARCHAR(255) DEFAULT '';  
         
        SELECT DICNAME INTO dicText FROM sys_dic WHERE DICDEFINE = define and DICCODE = `code`;  
       
        RETURN dicText;  
      end;
CREATE DEFINER=`root`@`localhost` FUNCTION `getOrgName`(`code` VARCHAR(255)) RETURNS varchar(255) CHARSET utf8
BEGIN
	DECLARE orgName VARCHAR(255) DEFAULT '';

	SELECT ORG_NAME INTO orgName FROM sys_org WHERE ORG_CODE = `code`;  

	RETURN orgName;
END;
BEGIN;
LOCK TABLES `fast-admin`.`sys_dic` WRITE;
DELETE FROM `fast-admin`.`sys_dic`;
INSERT INTO `fast-admin`.`sys_dic` (`ID`,`DICDEFINE`,`DICDESC`,`DICCODE`,`DICNAME`,`ISUSE`,`CRT_USER`,`CRT_TIME`) VALUES ('1', 'sex', '性别', '1', '男', '1', 'admin', '2018-06-08'),('2', 'sex', '性别', '0', '女', '1', 'admin', '2018-06-08'),('3', 'isuse', '使用状态', '1', '使用中', '1', 'admin', '2018-06-08'),('4', 'isuse', '使用状态', '0', '已注销', '1', 'admin', '2018-06-08'),('5', 'menuType', '菜单类型', '0', '目录', '1', 'admin', '2018-06-08'),('6', 'menuType', '菜单类型', '1', '菜单', '1', 'admin', '2018-06-08'),('7', 'menuType', '菜单类型', '2', '按钮', '1', 'admin', '2018-06-08');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fast-admin`.`sys_menu` WRITE;
DELETE FROM `fast-admin`.`sys_menu`;
INSERT INTO `fast-admin`.`sys_menu` (`ID`,`MENU_ID`,`MENU_NAME`,`MENU_PID`,`MENU_URL`,`MENU_PARM`,`MENU_TYPE`,`MENU_ICON`,`MENU_SORT`,`CREATE_TIME`,`CREATE_USER`,`CREATE_ORG`,`UPDATE_TIME`,`UPDATE_USER`,`UPDATE_ORG`) VALUES ('1', '1', '商品列表', '0', NULL, NULL, '0', '&#xe6c9;', '3', NULL, NULL, NULL, '2018-07-04', 'admin', '1'),('10', '401', '用户管理', '4', '/sys/user/list', NULL, '1', '&#xe665;', '1', NULL, NULL, NULL, NULL, NULL, NULL),('103740af79724973a8969dc70ea24980', '40402', '修改', '404', NULL, 'org:update', '2', NULL, NULL, '2018-07-08', 'admin', '1', NULL, NULL, NULL),('11', '40101', '新增', '401', NULL, 'user:add', '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),('12', '40102', '修改', '401', NULL, 'user:update', '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),('13', '40103', '删除', '401', NULL, 'user:del', '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),('14', '40104', '重置密码', '401', NULL, 'user:resetPwd', '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),('15', '402', '菜单管理', '4', '/sys/menu/list', NULL, '1', '&#xe658;', '2', NULL, NULL, NULL, NULL, NULL, NULL),('16', '40201', '新增', '402', NULL, 'menu:add', '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),('17', '40202', '修改', '402', NULL, 'menu:update', '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),('18', '40203', '删除', '402', NULL, 'menu:del', '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),('2', '101', '列表一', '1', NULL, NULL, '1', '&#xe676;', '10', NULL, NULL, NULL, NULL, NULL, NULL),('2026ff6c77284e0093cb10e63d07da10', '40502', '修改', '405', NULL, 'dic:update', '2', NULL, NULL, '2018-07-08', 'admin', '1', NULL, NULL, NULL),('3', '102', '列表二', '1', NULL, NULL, '1', '&#xe66b;', '2', NULL, NULL, NULL, NULL, NULL, NULL),('4', '103', '列表三', '1', NULL, NULL, '1', '&#xe66e;', '3', NULL, NULL, NULL, NULL, NULL, NULL),('415b39ea8c044b5d833430a64f38be04', '40403', '删除', '404', NULL, 'org:del', '2', NULL, NULL, '2018-07-08', 'admin', '1', NULL, NULL, NULL),('4334e39da6d24960bb6130e158994648', '40302', '修改', '403', NULL, 'role:update', '2', NULL, '2', '2018-07-04', 'admin', '1', NULL, NULL, NULL),('5', '2', '解决方案', '0', NULL, NULL, '0', '&#xe702;', '2', NULL, NULL, NULL, '2018-07-18', 'admin', '1'),('57612dd7f93745b9bfee457410d646aa', '405', '字典管理', '4', '/sys/dic/list', NULL, '1', 'fa-fire', '5', '2018-07-06', 'admin', '1', '2018-07-06', 'admin', '1'),('6', '201', '方案一', '2', '/list', NULL, '1', '&#xe6b1;', '1', NULL, NULL, NULL, '2018-07-18', 'admin', '1'),('6e47f2d97a3e49ad98d97e9d985aece8', '40501', '新增', '405', NULL, 'dic:add', '2', NULL, '1', '2018-07-08', 'admin', '1', NULL, NULL, NULL),('7', '202', '方案二', '2', '', NULL, '1', '&#xe66a;', '2', NULL, NULL, NULL, '2018-07-18', 'admin', '1'),('71b57b4634f34bf39cbd2c797c94b42d', '40503', '删除', '405', NULL, 'dic:del', '2', NULL, NULL, '2018-07-08', 'admin', '1', NULL, NULL, NULL),('77b30d2bc73a45d6ae21e3606458b0d6', '40303', '删除', '403', NULL, 'role:del', '2', NULL, NULL, '2018-07-04', 'admin', '1', NULL, NULL, NULL),('8', '3', '发布商品', '0', '', NULL, '0', '&#xe7ae;', '8', '2018-06-21', '', '', '2018-07-06', 'admin', '1'),('85f129b57df04d3bbe34c28eb0599fec', '404', '单位管理', '4', '/sys/org/list', NULL, '1', 'fa-asterisk', '4', '2018-07-05', 'admin', '1', '2018-07-05', 'admin', '1'),('9', '4', '系统管理', '0', NULL, NULL, '0', '&#xe65d;', '1', '2018-06-21', '', '', '2018-07-04', 'admin', '1'),('9ccf7ce73d084144ac87d1d2553ce0c4', '50101', 'add', '501', NULL, 'test:add', '2', 'fa-plus', NULL, '2018-07-02', 'admin', '1', NULL, NULL, NULL),('a50bef14830c4327addddddd2717e41c', '40401', '新增', '404', NULL, 'org:add', '2', NULL, NULL, '2018-07-08', 'admin', '1', NULL, NULL, NULL),('b13bb9138c694186aa16bc018e458943', '40301', '新增', '403', NULL, 'role:add', '2', NULL, '1', '2018-07-04', 'admin', '1', NULL, NULL, NULL),('c5d03ff0e0b24917a13451611f489e03', '5', 'test', '0', NULL, NULL, '0', '&#xe6b1;', '5', '2018-07-02', 'admin', '1', NULL, NULL, NULL),('d73575540b1c4b0f9e88d9d4d25c5ce4', '403', '角色管理', '4', '/sys/role/list', NULL, '1', '&#xe653;', '3', '2018-07-02', 'admin', '1', NULL, NULL, NULL),('e5bbc6c38c1a4eada0b45c542e74b0ac', '501', 'test1', '5', 'xxx', NULL, '1', '&#xe66c;', '1', '2018-07-02', 'admin', '1', NULL, NULL, NULL),('fc65c4b0ad76432c8a4b30f852b3346f', '50102', 'del', '501', NULL, 'test:del', '2', NULL, NULL, '2018-07-05', 'admin', '1', '2018-07-08', 'admin', '1');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fast-admin`.`sys_org` WRITE;
DELETE FROM `fast-admin`.`sys_org`;
INSERT INTO `fast-admin`.`sys_org` (`ID`,`ORG_CODE`,`ORG_NAME`,`ORG_PID`,`STATUE`,`MEMO`,`CREATE_TIME`,`CREATE_USER`,`CREATE_ORG`,`UPDATE_TIME`,`UPDATE_USER`,`UPDATE_ORG`) VALUES ('1', '1', 'IT科技公司', '0', '1', NULL, NULL, NULL, NULL, '2018-07-18', 'admin', '1'),('10', '10302', '采购部', '103', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL),('2', '101', '软件部', '1', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL),('3', '102', '技术部', '1', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL),('4', '103', '行政部', '1', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL),('5', '104', '销售部', '1', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL),('6', '10101', '研发一部', '101', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL),('7', '10102', '研发二部', '101', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL),('8', '10103', '数据部', '101', '1', NULL, NULL, NULL, NULL, '2018-07-18', 'admin', '1'),('9', '10301', '财务部', '103', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fast-admin`.`sys_role` WRITE;
DELETE FROM `fast-admin`.`sys_role`;
INSERT INTO `fast-admin`.`sys_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_CODE`,`ROLE_STATUS`,`CREATE_TIME`,`CREATE_USER`,`CREATE_ORG`) VALUES ('1', '管理员', 'admin', '1', '2018-07-02', NULL, NULL),('2', '普通用户', 'user', '1', '2018-07-02', NULL, NULL),('afbeab7e44e64f1682ce3654e9785cbc', '11', '11', '1', '2018-07-06', 'admin', '1'),('e0f81201d9904d67826f6a0c1fa70150', 'test', 'test', '1', '2018-07-03', 'admin', '1');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fast-admin`.`sys_role_menu` WRITE;
DELETE FROM `fast-admin`.`sys_role_menu`;
INSERT INTO `fast-admin`.`sys_role_menu` (`ID`,`ROLE_ID`,`MENU_ID`,`STATUES`,`CREATE_TIME`,`CREATE_USER`,`CREATE_ORG`) VALUES ('03df78f0082045d1a3b2570295810246', '1', '40203', '1', '2018-07-08', 'admin', '1'),('03f8d644c24046579eb6fa644f3f4a04', '1', '40401', '1', '2018-07-08', 'admin', '1'),('048d051b46ae4f03a3a7594f0579e0c3', '1', '40503', '1', '2018-07-08', 'admin', '1'),('071577c7609745279b4ceedb4a1c187d', 'e0f81201d9904d67826f6a0c1fa70150', '401', '1', '2018-07-04', 'admin', '1'),('0c1d96477a70409ea0a3a9f058c245d2', 'e0f81201d9904d67826f6a0c1fa70150', '4', '1', '2018-07-04', 'admin', '1'),('1cf0761b8b164d8597693885b4de926a', '1', '40502', '1', '2018-07-08', 'admin', '1'),('1e61ea030d2b421c826f04ff7237c4a6', '1', '404', '1', '2018-07-08', 'admin', '1'),('1ff9a2cecff845a9b6401e7b592ab867', '1', '3', '1', '2018-07-08', 'admin', '1'),('2435e5c2c2cf49cb88a2acfcb36937d0', 'e0f81201d9904d67826f6a0c1fa70150', '101', '1', '2018-07-04', 'admin', '1'),('310616f842b642a695de607546ce302e', '2', '1', '1', '2018-07-04', 'admin', '1'),('340956f332c340d68095e911777d31a7', '1', '40103', '1', '2018-07-08', 'admin', '1'),('3b7edf0f452f4b1985b7f858dd6dc609', 'e0f81201d9904d67826f6a0c1fa70150', '102', '1', '2018-07-04', 'admin', '1'),('474769387f0349ff9559f65254180ecd', 'e0f81201d9904d67826f6a0c1fa70150', '201', '1', '2018-07-04', 'admin', '1'),('54e01784980348378b867171e4b29b22', 'e0f81201d9904d67826f6a0c1fa70150', '0', '1', '2018-07-04', 'admin', '1'),('63323effb0d0468b9385ce029f37cb22', '1', '40403', '1', '2018-07-08', 'admin', '1'),('633e184bf65e4d2cbf3bfee5ca8f10c5', '1', '40501', '1', '2018-07-08', 'admin', '1'),('6548b24ba37e42fd8c2d65ac0c095769', 'e0f81201d9904d67826f6a0c1fa70150', '1', '1', '2018-07-04', 'admin', '1'),('6cc781856ba54a50b26bfeec73e424f1', '1', '40101', '1', '2018-07-08', 'admin', '1'),('704a316959d74d6895ffaf7ebecec759', 'e0f81201d9904d67826f6a0c1fa70150', '202', '1', '2018-07-04', 'admin', '1'),('711c12eaab764463847d31d7fc190f9d', '2', '4', '1', '2018-07-04', 'admin', '1'),('78848e0652f9420d81fb5c1c7827ef6d', '1', '40302', '1', '2018-07-08', 'admin', '1'),('7d093b3ab6d64219912e883559954779', 'afbeab7e44e64f1682ce3654e9785cbc', '50101', '1', '2018-07-18', 'admin', '1'),('80f4306fb4fd451b995667cdac1f7a22', '1', '1', '1', '2018-07-08', 'admin', '1'),('856cd23142b249238bd170e54a94d1f9', '1', '201', '1', '2018-07-08', 'admin', '1'),('859a808a78d14321bed27d0e3add6de3', '1', '0', '1', '2018-07-08', 'admin', '1'),('8ec446f758a14f93a11808fddfad0d0e', '1', '202', '1', '2018-07-08', 'admin', '1'),('8eda5af323cd403b9c51012f33ef9b6f', '1', '405', '1', '2018-07-08', 'admin', '1'),('942d41c23a3a4248939eb38b6374aa47', '1', '102', '1', '2018-07-08', 'admin', '1'),('96c435f1edd34edd8ae27cb7c8397735', 'afbeab7e44e64f1682ce3654e9785cbc', '0', '1', '2018-07-18', 'admin', '1'),('98d768515fcc4d6d9850982e1799254a', '1', '101', '1', '2018-07-08', 'admin', '1'),('a18a6c14f6524ecfa916c8595e30f551', '1', '40303', '1', '2018-07-08', 'admin', '1'),('a2f219fa60794d13b1d9a9012405d279', '2', '102', '1', '2018-07-04', 'admin', '1'),('a609656240e048e2934545af2068d198', '1', '40301', '1', '2018-07-08', 'admin', '1'),('a623d7c6c11c444fa7afcaa9f4d2d8d4', '2', '40301', '1', '2018-07-04', 'admin', '1'),('abe27c456a6a432db8652fdc8acf2a3e', '2', '403', '1', '2018-07-04', 'admin', '1'),('af94eff45faa429b87109414b520c9bd', '1', '403', '1', '2018-07-08', 'admin', '1'),('b0dd70b2b82643bd9e63ed5d79e50930', '2', '401', '1', '2018-07-04', 'admin', '1'),('b8eae2c5ca78467b9abfb0c712582fc7', '1', '40104', '1', '2018-07-08', 'admin', '1'),('ba87097a511d402a8fdda420cc396619', '1', '103', '1', '2018-07-08', 'admin', '1'),('c0ce18d8921c4196b816d64561e86681', 'afbeab7e44e64f1682ce3654e9785cbc', '501', '1', '2018-07-18', 'admin', '1'),('cd6e3cf3565443809329e73ed98724b3', 'e0f81201d9904d67826f6a0c1fa70150', '2', '1', '2018-07-04', 'admin', '1'),('cf114e50491a4265aeacc888d60c59d0', '1', '4', '1', '2018-07-08', 'admin', '1'),('cf15a1e3eeb44c579b985bcdccf48c02', 'afbeab7e44e64f1682ce3654e9785cbc', '5', '1', '2018-07-18', 'admin', '1'),('d0f0b1bf10af4aeabba330c58b814a6c', '2', '103', '1', '2018-07-04', 'admin', '1'),('d46d5ea3d0394697ae7736ce08edeb60', '1', '40201', '1', '2018-07-08', 'admin', '1'),('d51ed7e9bd474784b29db4b1940b632a', '1', '40202', '1', '2018-07-08', 'admin', '1'),('d6519b48a5934880a2ffcc0c15ff5b3e', '2', '101', '1', '2018-07-04', 'admin', '1'),('e22568868baa41a4beb02494723590f5', '1', '2', '1', '2018-07-08', 'admin', '1'),('e7204489f736411abdc6e68e1eb61589', '1', '401', '1', '2018-07-08', 'admin', '1'),('eb13d97a689749158ccec4cdd40c0ccf', '1', '40402', '1', '2018-07-08', 'admin', '1'),('efee85ab210d4704a3bd12bcd516be38', '1', '40102', '1', '2018-07-08', 'admin', '1'),('f0880f75f2df4b41ae440cc1d4cea62a', 'e0f81201d9904d67826f6a0c1fa70150', '40101', '1', '2018-07-04', 'admin', '1'),('f3a36cdfe7ca4ff8b3d7cb1a7ba9818e', 'e0f81201d9904d67826f6a0c1fa70150', '103', '1', '2018-07-04', 'admin', '1'),('f5aa9ed25b1d4d95b5ca3ad175bca019', '1', '402', '1', '2018-07-08', 'admin', '1'),('faa792c3445847108e2dc90ada5d4b2a', '2', '40101', '1', '2018-07-04', 'admin', '1');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fast-admin`.`sys_role_user` WRITE;
DELETE FROM `fast-admin`.`sys_role_user`;
INSERT INTO `fast-admin`.`sys_role_user` (`ID`,`ROLE_ID`,`USER_ID`,`STATUES`,`CREATE_TIME`,`CREATE_USER`,`CREATE_ORG`) VALUES ('3c85915321a34bd3a3437f536ba8c557', '2', 'test', '1', '2018-07-04', 'admin', '1'),('57925ebca6cb43ec99485db8ee799884', '1', 'admin', '1', '2018-07-04', 'admin', '1'),('6245d3982cf84b478171254c0ab5b941', 'afbeab7e44e64f1682ce3654e9785cbc', '11', '1', '2018-07-18', 'admin', '1'),('fe23381a286f4847bde61936e0b0d659', 'e0f81201d9904d67826f6a0c1fa70150', 'test', '1', '2018-07-04', 'admin', '1');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `fast-admin`.`sys_user` WRITE;
DELETE FROM `fast-admin`.`sys_user`;
INSERT INTO `fast-admin`.`sys_user` (`ID`,`LOGINID`,`USERNAME`,`PASSWORD`,`SALT`,`ORGID`,`TELPHONE`,`EMAIL`,`ADDR`,`STATUE`,`MEMO`,`CREATE_TIME`,`CREATE_USER`,`CREATE_ORG`) VALUES ('1', 'admin', '管理员', '0b0a392fb465cd948d3d94d82b25776d', 'r1S0c1Rvw9fzoUU4', '1', NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL),('1c10c112b00a47729ab9bce1e81f4534', '123', '1123', '9c94e1730c574726037ad58c0f6210b6', 'saFYiwrYIVnNIa0S', NULL, '11111111111', '11@qq.com', '123', '0', NULL, NULL, NULL, NULL),('a29d4214386d4857a9233fe4c5af3638', 'test', '测试用户', '211165a317c4de5c52154e7bbfd24d5b', '2zeEU7J0H3FRvy65', '10103', '12345678911', '123456@qq.com', 'test', '1', NULL, '2018-06-25', 'admin', NULL),('af0f2dac9a5c4af5bf0c8c65294f5ac8', '11', '11', '57a5158860ca791c1652e44c3f989b11', 'lHBXgZwa8oXqSyzK', '104', '11111111111', '11@qq.com', '11', '1', NULL, '2018-06-27', 'admin', NULL),('e6802c17c52646479f7d1427bc6d5b48', 'test1', '测试', 'd913d97d61c423d85b813757f59b9358', 'FmYuAUYx4YRaYdXj', '10103', '11111111111', '111@qq.com', NULL, '1', NULL, '2018-07-03', 'admin', '1');
UNLOCK TABLES;
COMMIT;
