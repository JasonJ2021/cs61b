import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * grid is referred to as a "tile".
     * <ul>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel
     *     (LonDPP) possible, while still covering less than or equal to the amount of
     *     longitudinal distance per pixel in the query box for the user viewport size. </li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the
     *     above condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */
    private int computeDepth(double LonDppQuery) {
        double LonDppImage = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
        int depth = 0;
        while (LonDppImage > LonDppQuery && depth < 7) {
            LonDppImage /= 2;
            depth++;
        }
        return depth;
    }

    private int computeXStart(double ImageWidth, double leftX) {
        int startX = (int) ((leftX - MapServer.ROOT_ULLON) / ImageWidth);
        return startX;
    }

    private int computeYStrat(double ImageHeight, double upY) {
        int startY = (int) ((MapServer.ROOT_ULLAT - upY) / ImageHeight);
        return startY;
    }

    private int computeXEnd(double ImageWidth, double rightX) {
//        int endX = (int) (((rightX - MapServer.ROOT_ULLON) / ImageWidth));
//        if(rightX == MapServer.ROOT_ULLON + endX*ImageWidth){
//            endX--;
//        };
//        return endX;
        int endX = 0;
        System.out.println(rightX);
        double rightEnd = MapServer.ROOT_ULLON + (endX + 1) * ImageWidth;
        while (rightEnd < rightX && rightEnd < MapServer.ROOT_LRLON) {
            endX++;
            rightEnd = MapServer.ROOT_ULLON + (endX + 1) * ImageWidth;
        }

        return endX;
    }

    private int computeYEnd(double ImageHeight, double downY) {
//        int endY = (int) (((MapServer.ROOT_ULLAT - downY) / ImageHeight));
//        if(downY == MapServer.ROOT_ULLAT - endY*ImageHeight){
//            endY--;
//        };
//        return endY;
        int endY = 0;
        double downEnd = MapServer.ROOT_ULLAT - endY * ImageHeight - ImageHeight;
        while (downEnd > downY && downEnd > MapServer.ROOT_LRLAT) {
            endY++;
            downEnd = MapServer.ROOT_ULLAT - endY * ImageHeight - ImageHeight;
        }
        return endY;
    }

    public Map<String, Object> getMapRaster(Map<String, Double> params) {
//        System.out.println(params);

        double lrlon = params.get("lrlon");
        double ullong = params.get("ullon");
        double w = params.get("w");
        double h = params.get("h");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");

        double LonDppQuery = Math.abs(lrlon - ullong) / w;
        int depth = computeDepth(LonDppQuery);

        double ImageWidth = Math.abs(MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / Math.pow(2, depth);
        double ImageHeight = Math.abs(MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / Math.pow(2, depth);

        int startX = computeXStart(ImageWidth, ullong);
        int startY = computeYStrat(ImageHeight, ullat);
        int endX = computeXEnd(ImageWidth, lrlon);
        int endY = computeYEnd(ImageHeight, lrlat);

        double raster_ul_lon = MapServer.ROOT_ULLON + startX * ImageWidth;
        double raster_ul_lat = MapServer.ROOT_ULLAT - startY * ImageHeight;
        double raster_lr_lon = MapServer.ROOT_ULLON + (endX + 1) * ImageWidth;
        double raster_lr_lat = MapServer.ROOT_ULLAT - (endY + 1) * ImageHeight;
        String[][] render_grid = new String[endY - startY + 1][endX - startX + 1];
        for(int i = startY ; i <=endY ; i++){
            for(int j = startX ; j <= endX ;j++){
                //d1_x0_y1.png
                render_grid[i-startY][j-startX] = String.format("d%d_x%d_y%d.png",depth ,  j , i );
            }
        }

        Map<String, Object> results = new HashMap<>();
        results.put("raster_ul_lon" , raster_ul_lon);
        results.put("raster_ul_lat" , raster_ul_lat);
        results.put("raster_lr_lon" , raster_lr_lon);
        results.put("raster_lr_lat" , raster_lr_lat);
        results.put("depth" , depth);
        results.put("render_grid" , render_grid);
        results.put("query_success" , true);

        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                + "your browser.");
        return results;
    }

}
