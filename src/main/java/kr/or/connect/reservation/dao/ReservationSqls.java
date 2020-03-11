package kr.or.connect.reservation.dao;

public class ReservationSqls {
	// ProductDao
	public static final String SELECT_CATEGORY_PRODUCT = "select t2.id as productId, t2.display_id as displayInfoId, t1.save_file_name as productImageUrl, t2.description as productDescription, t2.place_name as placeName, t2.content as productContent from (select product_image.product_id, file_info.save_file_name from product_image, file_info  where product_image.file_id = file_info.id and type='th') t1, (select product.id, display_info.id as display_id, description, place_name,content from product, display_info where product.id = display_info.product_id and product.category_id = :category) t2 where t1.product_id = t2.id limit :start, :end";
	public static final String SELECT_PRODUCT = "select t2.id as productId, t2.display_id as displayInfoId, t1.save_file_name as productImageUrl, t2.description as productDescription, t2.place_name as placeName, t2.content as productContent from (select product_image.product_id, file_info.save_file_name from product_image, file_info  where product_image.file_id = file_info.id and type='th') t1, (select product.id, display_info.id as display_id, description, place_name,content from product, display_info where product.id = display_info.product_id) t2 where t1.product_id = t2.id limit :start, :end";
	public static final String SELECT_COUNT = "select count(*) from product, display_info where product.id = display_info.product_id";
	public static final String SELECT_CATEGORY_COUNT = "select count(product.id) as count, category_id as id  from product, display_info where product.id = display_info.product_id group by category_id;";
	public static final String SELECT_PRODUCT_ID = "select product_id as productId from display_info where id = :displayId";

	// PromotionDao
	public static final String SELECT_PROMOTION = "select t2.id, t2.product_id as productId, t1.productImageUrl from (select product_image.product_id, file_info.save_file_name as productImageUrl from product_image, file_info  where product_image.file_id = file_info.id and type='th') t1, promotion t2 where t1.product_id = t2.product_id";

	// CategoryDao
	public static final String SELECT_CATEGORY = "select * from category";
	public static final String SELECT_CATEGORY_NAME = "select name from category where id = :categoryId";

	// DetailProductDao
	public static final String SELECT_PRODUCT_IMAGE = "select content_type as contentType, create_date as createDate, delete_flag as deleteFlag, file_info.id as fileInfoId, file_name as fileName, modify_date as modifyDate, product_id as productId, product_image.id as productImageId, save_file_name as saveFileName, type from product_image, file_info where product_image.file_id = file_info.id and product_image.product_id = :productId";
	public static final String SELECT_PRODUCT_PRICE = "select create_date as createDate, product_id as productId, discount_rate as discountRate, modify_date as modifyDate, price, price_type_name as priceTypeName ,id as productPriceId from product_price where  product_id = :productId";
	public static final String SELECT_DISPLAY_IMAGE = "select content_type as contentType, create_date as createDate, delete_flag as deleteFlag, display_info_id as displayInfoId, t1.id as displayInfoImageId, file_id as fileId, file_name as fileName, modify_date as modifyDate, save_file_name as saveFileName  from display_info_image t1, file_info t2 where t1.file_id = t2.id and t1.display_info_id= :displayInfoId";
	public static final String SELECT_DISPLAY = "select category_id as categoryId, t2.create_date as createDate, t2.id as displayInfoId, email, homepage, t2.modify_date as modifyDate, opening_hours as openingHours, place_lot as placeLot,  place_name as placeName,  place_street as placeStreet, content as productContent, description as productDescription, event as productEvent, t1.id as productId, tel as telephone from product t1, display_info t2 where t1.id = t2.product_id and t2.id = :displayInfoId";
	public static final String SELECT_MA_IMAGE = "select save_file_name from product_image t1, file_info t2 where t1.file_id = t2.id and type='ma' and product_id = :productId";
	public static final String SELECT_ET_IMAGE = "select save_file_name from product_image t1, file_info t2 where t1.file_id = t2.id and type='et' and product_id = :productId limit 1";
	
	// CommentDao
	public static final String SELECT_COMMENT = "select comment, t2.id as commentId,  t2.create_date as createDate, t2.modify_date as modifyDate, t2.product_id as productId, reservation_name as reservationName, reservation_tel as reservationTelephone , reservation_email as reservationEmail, reservation_date as reservationDate,  t1.id as reservationInfoId, score from reservation_info t1, reservation_user_comment t2 where t1.id = t2.reservation_info_id and t2.product_id= :productId";
	public static final String SELECT_COMMENT_IMAGE = "select content_type as contentType, create_date as createDate, delete_flag as deleteFlag, file_id as fileId, file_name as fileName, t2.id as imageId, modify_date as modifyDate, reservation_info_id as reservationInfoId, reservation_user_comment_id as reservationUserCommentId, save_file_name as saveFileName from file_info t1, reservation_user_comment_image t2 where t2.file_id = t1.id and t2.reservation_user_comment_id = :commentId";
	public static final String SELECT_COMMENT_COUNT = "select count(*) from reservation_user_comment where product_id = :productId";
	
	//ReservationDao
	public static final String SELECT_BOOKING = "select id as reservationInfoId, product_id as productId, display_info_id as displayInfoId,  reservation_name as reservationName, reservation_tel as reservationTelephone, reservation_email as reservationEmail, reservation_date as reservationDate, cancel_flag as cancelYn, create_date as createDate, modify_date as modifyDate from reservation_info where reservation_email = :email";
	public static final String SELECT_TOTAL_PRICE = "select sum(t3.multi) as totalPrice from (select t1.count * t2.price as multi, t1.reservation_info_id, t1.count, t1.product_price_id, t2.price_type_name, t2.price from reservation_info_price t1, product_price t2 where t1.product_price_id = t2.id and t1.reservation_info_id = :reservationInfoId) t3";
	public static final String CANCEL_BOOKING = "update reservation_info set cancel_flag = 1 where id= :reservationId";
	public static final String SELECT_BOOKING_BY_ID = "select id as reservationInfoId, product_id as productId, display_info_id as displayInfoId,  reservation_name as reservationName, reservation_tel as reservationTelephone, reservation_email as reservationEmail, reservation_date as reservationDate, cancel_flag as cancelYn, create_date as createDate, modify_date as modifyDate from reservation_info where id = :reservationId";
	public static final String SELECT_BOOKING_PRICE_BY_ID = "select * from reservation_info_price where reservation_info_id = :reservationId";
	public static final String SELECT_PRODUCT_BY_ID = "select product_id from reservation_info  where id = :reservationId";
	
	//ImageFileDao
	public static final String SELECT_FILE = "select * from file_info where id = :imageId;";

}
